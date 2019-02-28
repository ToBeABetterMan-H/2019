#pragma once

#include "Module.h"
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <boost/make_shared.hpp>

#define CONFIG_PATH "request/config.xml"

using boost::shared_ptr;
using namespace boost::property_tree;

class Config: public Module
{
public:
	static shared_ptr<Config> getInstance()
	{
		static shared_ptr<Config> config = shared_ptr<Config>(new Config());
		return config;
	}

	boost::property_tree::ptree getTree()
	{
		return m_ptree;
	}

	bool init(boost::shared_ptr<Module> mod,int argc,char** argv);
	void ready(boost::shared_ptr<Module> mod);
	void run(boost::shared_ptr<Module> mod);
	void stop(boost::shared_ptr<Module> mod);

private:
	Config()
	{
		m_sName = "Config";
		m_iPriority = Core_Hight;
		m_iState = STATE_STOP;
	}
	Config(Config const &config);
	Config& operator = (Config const &config);

private:
	boost::property_tree::ptree m_ptree;
	std::string m_sPath;
};

bool Config::init(boost::shared_ptr<Module> mod,int argc,char** argv)
{
	// if(argv[1])
	// {
		m_sPath = CONFIG_PATH;
		try
		{
			read_xml(m_sPath,m_ptree);
			return true;
		}catch(...)
		{
			std::cout << "Failed to read configuration file !!!" << std::cout;
			return false;
		}
	// }
	// std::cout << "Have no config file !!!" << std::endl;
	// return false;
}
void Config::ready(boost::shared_ptr<Module> mod)
{
}
void Config::run(boost::shared_ptr<Module> mod)
{
}
void Config::stop(boost::shared_ptr<Module> mod)
{
}

MODULE_AUTO_REGIST (regist)
{
	ModuleManage::getInstance()->Module_Regist(boost::shared_ptr<Module>(Config::getInstance()));
}

