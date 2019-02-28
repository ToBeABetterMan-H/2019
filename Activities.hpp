#include "Plugin.h"
#include "Module.h"
#include "User.h"

#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <boost/make_shared.hpp>
#include <map>

using namespace IfThrift;
using namespace boost::property_tree;

using boost::shared_ptr;

#define COUT_RETURN(...)\
	std::cout << "result ---> "\
	<< __VA_ARGS__ << std::endl;

class FuncBase
{
public:
	virtual void execute(boost::shared_ptr<IfThrift::UserClient> client,boost::property_tree::ptree &pt) = 0;
};

/* 用户登录 */
class Login:public FuncBase
{
public:
	void execute(boost::shared_ptr<IfThrift::UserClient> client,boost::property_tree::ptree &pt)
	{
		IfThrift::UserIdPack user;
		std::string name = pt.get<std::string>("login.name");
		std::string phone = pt.get<std::string>("login.phone");
		std::string email = pt.get<std::string>("login.email");
		std::string password = pt.get<std::string>("login.password");
		std::string reserved = pt.get<std::string>("login.reserved");
		
		client->login(user,password,name,phone,email,reserved);
		COUT_RETURN("code = " << user.code << " userId = " << user.userId);
	}
};

/* 获取环境侦测统计数据 */
class Topo_GetEnvStatData:public FuncBase
{
public:
	void execute(boost::shared_ptr<IfThrift::UserClient> client,boost::property_tree::ptree &pt)
	{
		::VSAP::StruVSAPSimpleListRsp _return;
		::VSAP::StruVSAPSimpleReq req;
		std::map<::DevGW::EnuParamName::type,std::string> paramMap;

		paramMap[::DevGW::EnuParamName::type::VSAPUserID] = pt.get<std::string>("Topo_GetEnvStatData.VSAPUserID");
		paramMap[::DevGW::EnuParamName::type::FunName] = pt.get<std::string>("Topo_GetEnvStatData.FunName");
		paramMap[::DevGW::EnuParamName::type::TopoID] = pt.get<std::string>("Topo_GetEnvStatData.TopoID");
		paramMap[::DevGW::EnuParamName::type::Type] = pt.get<std::string>("Topo_GetEnvStatData.Type");
		paramMap[::DevGW::EnuParamName::type::StartTime] = pt.get<std::string>("Topo_GetEnvStatData.StartTime");
		paramMap[::DevGW::EnuParamName::type::Count] = pt.get<std::string>("Topo_GetEnvStatData.Count");
		
		req.ParamMap = paramMap;
		client->SimpleListRequest(_return, req);
		COUT_RETURN(_return.Ret << " TotalCount = " << _return.TotalCount);

		for(auto return_info: _return.Infos)
		{
			paramMap = return_info.ParamMap;
			COUT_RETURN(
				" pm2.5 = " << paramMap.find(::DevGW::EnuParamName::ED_PM2_5)->second << 
				" pm10 = " << paramMap.find(::DevGW::EnuParamName::ED_PM10)->second <<
				" tsp = " << paramMap.find(::DevGW::EnuParamName::ED_TSP)->second <<
				" noise = " << paramMap.find(::DevGW::EnuParamName::ED_NOISE)->second <<
				" temp = " << paramMap.find(::DevGW::EnuParamName::ED_TEMP)->second <<
				" airpre = " << paramMap.find(::DevGW::EnuParamName::ED_AIRPRE)->second <<
				" humidity = " << paramMap.find(::DevGW::EnuParamName::ED_HUMIDITY)->second <<
				" airspeed = " << paramMap.find(::DevGW::EnuParamName::ED_AIRSPEED)->second <<
				" airdir = " << paramMap.find(::DevGW::EnuParamName::ED_AIRDIR)->second <<
				" so2 = " << paramMap.find(::DevGW::EnuParamName::ED_SO2)->second <<
				" no2 = " << paramMap.find(::DevGW::EnuParamName::ED_NO2)->second <<
				" o3 = " << paramMap.find(::DevGW::EnuParamName::ED_O3)->second <<
				" co = " << paramMap.find(::DevGW::EnuParamName::ED_CO)->second <<
				" voc = " << paramMap.find(::DevGW::EnuParamName::ED_VOC)->second
			);
		}
	}
};


MODULE_AUTO_REGIST(registerFunc)
{
	Plugin::getInstance()->regUser("login",boost::shared_ptr<FuncBase>(new Login()));
	Plugin::getInstance()->regUser("Topo_GetEnvStatData",boost::shared_ptr<FuncBase>(new Topo_GetEnvStatData()));
}