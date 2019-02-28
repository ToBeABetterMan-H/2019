#pragma once

#include "User.h"
#include <map>
#include <boost/make_shared.hpp>

using namespace IfThrift;

using boost::shared_ptr;

class FuncBase;

class Plugin
{
public:
	static boost::shared_ptr<Plugin> getInstance()
	{
		static boost::shared_ptr<Plugin> obj = boost::shared_ptr<Plugin>(new Plugin());
		return obj;
	}

	std::map<std::string, boost::shared_ptr<FuncBase>> getUserMap()
	{
		return m_userMap;
	}

	void regUser(const std::string name, boost::shared_ptr<FuncBase> pro)
	{
		m_userMap[name] = pro;
	}
private:
	Plugin(){}

private:
	std::map<std::string, boost::shared_ptr<FuncBase>> m_userMap;
};