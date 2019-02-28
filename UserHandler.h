#pragma once 

#include "User.h"
#include "Plugin.h"
#include "Activities.hpp"
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <boost/make_shared.hpp>

using namespace IfThrift;
using namespace boost::property_tree;

using boost::shared_ptr;

#define ACTION_PATH "request/test.xml"

class UserHandler
{
public:
	UserHandler(boost::shared_ptr<IfThrift::UserClient> client)
	{
		m_client = client;
		read_xml(ACTION_PATH,m_pt);
	}
	void execute(std::string action)
	{
		auto funcMap = Plugin::getInstance()->getUserMap();
		auto iter = funcMap.find(action);
		if(iter != funcMap.end())
		{
			iter->second->execute(m_client,m_pt);
			return;
		}
		std::cout << "not implemented" << std::endl;
	}
private:
	boost::shared_ptr<IfThrift::UserClient> m_client;
	boost::property_tree::ptree m_pt;
};