#include "Module.h"
#include <assert.h>
#include <algorithm>

std::string Module::getName()
{
	return m_sName;
}
std::vector<boost::shared_ptr<Module>> Module::getDepends()
{
	return m_vDepends;
}
std::vector<boost::shared_ptr<Module>> Module::getChildren()
{
	return m_vChildren;
}
int Module::getPriority()
{
	return m_iPriority;
}
int Module::getState()
{
	return m_iState;
}

void Module::setName(const std::string name)
{
	m_sName = name;
}
void Module::setPriority(int priority)
{
	m_iPriority = priority;
}
void Module::setState(int state)
{
	m_iState = state;
}

bool ModuleManage::iniModule(const boost::shared_ptr<Module> mod,int argc,char** argv)
{
	if(mod->getState() == STATE_INIT)
	{
		return true;
	}

	if(mod->getState() == STATE_INITING)
	{
		std::cout << "<<<<<< Endless Loop >>>>>>" << std::endl;
		return false;
	}

	mod->setState(STATE_INITING);	

	if(!mod->getDepends().empty())
	{
		for(auto depend:mod->getDepends())
		{
			iniModule(depend,argc,argv);
		}
	}

	std::cout << "<<<<<< now init moudule " << mod->getName() << ">>>>>>" << std::endl;
	if(! mod->init(mod,argc,argv))
	{
		return false;
	}
	std::cout << "<<<<<< init moudule finished " << mod->getName() << ">>>>>>" << std::endl;
	mod->setState(STATE_INIT);

	return true;
}

void ModuleManage::readyModule(const boost::shared_ptr<Module> mod)
{
	if(mod->getState() == STATE_READY)
	{
		return;
	}

	if(!mod->getDepends().empty())
	{
		for(auto depend:mod->getDepends())
		{
			readyModule(depend);
		}
	}

	std::cout << "<<<<<< now ready moudule " << mod->getName() << ">>>>>>" << std::endl;
	mod->ready(mod);
	std::cout << "<<<<<< ready moudule finished " << mod->getName() << ">>>>>>" << std::endl;
	mod->setState(STATE_READY);
}

void ModuleManage::runModule(const boost::shared_ptr<Module> mod)
{
	if(mod->getState() == STATE_RUN)
	{
		return;
	}

	if(!mod->getDepends().empty())
	{
		for(auto depend:mod->getDepends())
		{
			runModule(depend);
		}
	}

	std::cout << "<<<<<< now run moudule " << mod->getName() << ">>>>>>" << std::endl;
	mod->run(mod);
	std::cout << "<<<<<< run moudule finished " << mod->getName() << ">>>>>>" << std::endl;
	mod->setState(STATE_RUN);
}

void ModuleManage::stopModule(const boost::shared_ptr<Module> mod)
{
	if(mod->getState() == STATE_STOP)
	{
		return;
	}

	if(!mod->getChildren().empty())
	{
		for(auto child:mod->getChildren())
		{
			stopModule(child);
		}
	}

	std::cout << "<<<<<< now stop moudule " << mod->getName() << ">>>>>>" << std::endl;
	mod->stop(mod);
	std::cout << "<<<<<< stop moudule finished " << mod->getName() << ">>>>>>" << std::endl;
	mod->setState(STATE_STOP);
}

void ModuleManage::Module_Regist(boost::shared_ptr<Module> mod)
{
	assert(m_iSize < MAX_MODULE_SIZE); /* 断言：若表达式为 false 则退出程序 */
	m_vModule.push_back(mod);
}

bool ModuleManage::Module_Init(int argc,char** argv)
{
	assert(m_iState == STATE_STOP);
	m_iState = STATE_INIT;

	auto cmpModule = [](const boost::shared_ptr<Module> &mod1,const boost::shared_ptr<Module> &mod2)->bool
	{
		return mod1->getPriority() < mod2->getPriority();
	};

	std::sort(m_vModule.begin(),m_vModule.end(),cmpModule);	/* 根据优先级排序 module */

	for(auto mod:m_vModule)	/* 将所有 module 状态设置为 STATE_STOP */
	{
		if(mod == nullptr)
		{
			break;
		}
		mod->setState(STATE_STOP);

		if(!iniModule(mod,argc,argv))
		{
			return false;
		}		
	}

	return true;
}

void ModuleManage::Module_Ready()
{
	assert(m_iState == STATE_INIT);
	m_iState = STATE_READY;

	for(auto mod:m_vModule)
	{
		if(mod == nullptr)
		{
			break;
		}

		readyModule(mod);
	}
	return;
}

void ModuleManage::Module_Run()
{
	assert(m_iState == STATE_READY);
	m_iState = STATE_RUN;

	for(auto mod:m_vModule)
	{
		if(mod == nullptr)
		{
			break;
		}

		runModule(mod);
	}
	return;
}

void ModuleManage::Module_Stop()
{
	m_iState = STATE_STOP;

	for(auto mod:m_vModule)
	{
		if(mod == nullptr)
		{
			break;
		}

		stopModule(mod);		
	}
}

int  ModuleManage::Module_GetState()
{
	return m_iState;
}
