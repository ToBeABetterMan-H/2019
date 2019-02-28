#include "Module.h"
#include <iostream>
#include <unistd.h>
#include <signal.h>
#include <boost/make_shared.hpp>

using boost::shared_ptr;

static bool systemExist = false;

void recvTermSignal(int signo)
{
	systemExist = true;
}


int main(int argc, char** argv)
{
	signal(SIGTERM,recvTermSignal);
	signal(SIGINT,recvTermSignal);

	boost::shared_ptr<ModuleManage> manage = ModuleManage::getInstance();
	if(!manage->Module_Init(argc,argv))
	{
		std::cout << "<<<<<< Init false >>>>>>" << std::endl;
		return -1;
	}

	manage->Module_Ready();
	manage->Module_Run();

	while(!systemExist)
	{
		pause();
	}
	manage->Module_Stop();

	return 0;
}