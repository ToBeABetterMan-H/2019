#pragma once

#include "Module.h"
#include "Config.h"
#include "ActivitiWorkFlow.h"
#include <iostream>
#include <thread>
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/protocol/TCompactProtocol.h>
#include <thrift/transport/TBufferTransports.h>
#include <thrift/transport/TSocket.h>
#include <boost/make_shared.hpp>

using boost::shared_ptr;

using namespace ::apache::thrift;
using namespace ::apache::thrift::protocol;
using namespace ::apache::thrift::transport;

class ThriftMainBase
{
public:
	void ThriftMain();
};

template <class ClientType>
class ThriftClient
{
public:
	ThriftClient()
	{
		m_bConnet = false;

		if(loadConfig())
		{
			shared_ptr<TSocket> socket(new TSocket(m_sIp,m_iPort));
			m_transport = boost::make_shared<TFramedTransport>(socket);
			shared_ptr<TProtocol> protocol(new TCompactProtocol(m_transport));
			m_client = boost::make_shared<ClientType>(protocol);
		}
	}

	bool connect()
	{
		if(m_transport)
		{
			m_transport->open();
			m_bConnet = true;
			return true;
		}
		return false;
	}
	void close()
	{
		if(m_transport)
		{
			m_transport->close();
		}
		m_bConnet = false;
	}

	bool loadConfig()
	{
		try
		{
			m_sIp = Config::getInstance()->getTree().get<std::string>("Remote.COMMON.IP");
			m_iPort = Config::getInstance()->getTree().get<int>("Remote.COMMON.PORT");
			return true;
		}catch(...)
		{	
			std::cout << "Load config failed !!!" << std::endl;
			return false;
		}
	}

	boost::shared_ptr<ClientType> getInstance()
	{
		if(m_bConnet)
		{
			return m_client;	
		}
		return nullptr;
	}

public:
	bool m_bConnet;

protected:
	std::string m_sIp;
	int m_iPort;
	boost::shared_ptr<TTransport> m_transport;
	boost::shared_ptr<ClientType> m_client;
};