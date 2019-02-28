#include "ThriftClient.h"
#include "User.h"
#include "UserHandler.h"

using namespace IfThrift;

static void mainThread(ThriftClient<IfThrift::UserClient> *client);
int dialogue(StruGeneralRsp _return,StruGeneralReq &req);

class UserSystem: public Module
{
public:
	UserSystem()
	{
		m_sName = "UserSystem";
		m_iPriority = Low;
		m_iState = STATE_STOP;
	}

	bool init(boost::shared_ptr<Module> mod,int argc,char** argv);
	void ready(boost::shared_ptr<Module> mod);
	void run(boost::shared_ptr<Module> mod);
	void stop(boost::shared_ptr<Module> mod);

private:
	ThriftClient<IfThrift::UserClient> *m_client;
	boost::shared_ptr<std::thread> m_thread;
};

bool UserSystem::init(boost::shared_ptr<Module> mod,int argc,char** argv)
{
	try
	{
		m_client = new ThriftClient<IfThrift::UserClient>();
		return true;	
	}catch(...)
	{
		return false;
	}	
}

void UserSystem::ready(boost::shared_ptr<Module> mod)
{
	try
	{
		m_client->connect();
	}catch(...)
	{
		std::cout << "Connect UserSystem server failed !!!" << std::endl;
	}
}

void UserSystem::run(boost::shared_ptr<Module> mod)
{
	if(m_client->m_bConnet)
	{
		m_thread = boost::make_shared<std::thread>(mainThread,m_client);	
	}		
}

void UserSystem::stop(boost::shared_ptr<Module> mod)
{
	m_client->close();
}

static void mainThread(ThriftClient<IfThrift::UserClient> *client)
{
	UserHandler obj(client->getInstance());
	while(1)
	{
		try
		{
			std::string task;

			std::cout << "(UserSystem) What do you want to do --> ";
			std::cin >> task;

			if(task == "q")
			{
				break;
			}

			obj.execute(task);
		}catch(...)
		{
			std::cout << "Input error !!!" << std::endl;
			continue;
		}
	}

}

MODULE_AUTO_REGIST (registUserSystem)
{
	ModuleManage::getInstance()->Module_Regist(boost::shared_ptr<Module>(new UserSystem()));
}