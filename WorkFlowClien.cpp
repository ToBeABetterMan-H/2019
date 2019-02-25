#include "Module.h"
#include "ActivitiWorkFlow.h"
#include <iostream>
#include <string>
#include <thread>
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/protocol/TCompactProtocol.h>
#include <thrift/transport/TBufferTransports.h>
#include <thrift/transport/TSocket.h>
#include <boost/property_tree/ptree.hpp>
#include <boost/property_tree/xml_parser.hpp>
#include <boost/make_shared.hpp>

using namespace boost::property_tree;

using boost::shared_ptr;

using namespace ::apache::thrift;
using namespace ::apache::thrift::protocol;
using namespace ::apache::thrift::transport;

void ThreadMain(boost::shared_ptr<ActivitiWorkFlowClient> client);

class WorkFlow: public Module
{
public:
	static boost::shared_ptr<WorkFlow> getInstance()
	{
		static boost::shared_ptr<WorkFlow> workflow = boost::shared_ptr<WorkFlow>(new WorkFlow());
		return workflow;
	}

	void setClient(const std::string ip,int port)
	{
		shared_ptr<TSocket> socket(new TSocket(ip,port));
		shared_ptr<TTransport> transport(new TFramedTransport(socket));
		shared_ptr<TProtocol> protocol(new TCompactProtocol(transport));
		m_client = boost::make_shared<ActivitiWorkFlowClient>(protocol);
	}

	bool connect()
	{
		try
		{
			m_transport->open();
			m_bConnet = true;
			std::cout << "Connect workflow server success !!!" << std::endl;
			return true;
		}catch(...)
		{
			m_bConnet = false;
			std::cout << "Connect workflow server failed !!!" << std::endl;
		}
		return false;
	}

	boost::shared_ptr<ActivitiWorkFlowClient> getClient()
	{
		return m_client;
	}

	bool init(boost::shared_ptr<Module> mod,int argc,char** argv)
	{
		shared_ptr<TSocket> socket(new TSocket("192.168.226.121",20000));
		m_transport = boost::make_shared<TFramedTransport>(socket);
		shared_ptr<TProtocol> protocol(new TCompactProtocol(m_transport));
		m_client = boost::make_shared<ActivitiWorkFlowClient>(protocol);
		return true;
	}
	void ready(boost::shared_ptr<Module> mod)
	{
		try
		{
			m_transport->open();
			m_bConnet = true;
		}catch(...)
		{
			m_bConnet = false;
			std::cout << "Connect workflow server failed !!!" << std::endl;
		}
	}
	void run(boost::shared_ptr<Module> mod)
	{
		if(m_bConnet)
		{
			m_thread = boost::make_shared<std::thread>(ThreadMain,m_client);	
		}		
	}
	void stop(boost::shared_ptr<Module> mod)
	{
		m_transport->close();
	}

private:
	WorkFlow()
	{
		m_sName = "WorkFlow";
		m_iPriority = Low;
		m_iState = STATE_STOP;
		m_bConnet = false;
	}
	WorkFlow(WorkFlow const &wolflow);
	WorkFlow& operator = (WorkFlow const &wolflow);

private:
	boost::shared_ptr<TTransport> m_transport;
	boost::shared_ptr<ActivitiWorkFlowClient> m_client;
	boost::shared_ptr<std::thread> m_thread;
	bool m_bConnet;
};

int dialogue(StruGeneralRsp _return,StruGeneralReq &req)
{
	std::string xml_path;
	std::string task;
	std::string child_path;

	std::cout << "What do you want to do --> ";
	std::cin >> task;

	if(task == "q")
	{
		return -1;
	}

	xml_path = "WorkFlow/test.xml";

	ptree pt;
	read_xml(xml_path,pt);

	child_path = task + ".params";

	auto child = pt.get_child(child_path);
	for(auto i = child.begin();i != child.end();i++)
    {
    	req.params[i->first] = i->second.get_value<std::string>();
    	std::cout << i->first << " : " << i->second.get_value<std::string>() << std::endl;
    }

	return pt.get<int>(task + ".Type");
}

void ThreadMain(boost::shared_ptr<ActivitiWorkFlowClient> client)
{
	StruGeneralReq req;
	StruGeneralRsp _return;
	int ret;

	while(1)
	{
		try
		{
			ret = dialogue(_return,req);
		}catch(...)
		{
			std::cout << "Input error !!!" << std::endl;
			continue;
		}

		if(ret == 1)
		{
			try
			{
				client->ActivitiWorkFlowSearch(_return,req);
				_return.printTo(std::cout);
				std::cout << std::endl;
			}catch(...)
			{
				std::cout << "Workflow remote call failed !!!" << std::endl;
				// std::cout << "Try to call again ..." << std::endl;
			}
		}else if(ret == 2)
		{
			try
			{
				client->ActivitiWorkFlowUpdate(_return,req);
				_return.printTo(std::cout);
				std::cout << std::endl;
			}catch(...)
			{
				std::cout << "Workflow remote call failed !!!" << std::endl;
				// std::cout << "Try to call again ..." << std::endl;

			}
		}else
		{
			break;
		}
	}
};

// MODULE_AUTO_REGIST (regist)
// {
// 	ModuleManage::getInstance()->Module_Regist(boost::shared_ptr<Module>(WorkFlow::getInstance()));
// }