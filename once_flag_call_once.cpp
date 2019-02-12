/*
 * Author: wxh
 * Date: 20190212
 * Description: How to use the call_once && once_flag
*/

/* 
 * 作用：使得某一函数在整个进程中最多被执行一次，常用于多线程
*/

#include <iostream>
#include <thread>

/* 1.所需头文件 */
#include <mutex>

using namespace std;

/* 2.定义 once_flag ，once_flag 本质是一个类，该类不允许修改；它必需比使用它的线程生命周期要长，所以通常定义为全局变量。 */
std::once_flag myFlag;
std::once_flag myFlag2;

/* 若所要执行的函数在运行中抛出异常，那么将由另外的等待 once_flag 的线程执行该函数
 * 实际上 once_flag 相当于一个锁，使用它的线程都会在上面等待。仅仅只有一个线程同意运行。
 * 假设该线程抛出异常，那么从等待的线程中选择一个。反复上面的流程。
 */
void myPrint(bool isThrow)
{
	if(isThrow)
	{
		cout << "throw" << endl;
		throw exception();
	}
	cout << "hello once_flag !!!" << endl;
}

void runOnce(bool isThrow)
{
	try
	{
		/* 3.使用 call_once 需要传入一个 once_flag ，回调函数，参数列表（选填） */
		std::call_once(myFlag,myPrint,isThrow);
	}catch(...)
	{
	}
}

int main()
{
	thread td1(runOnce,true);
	thread td2(runOnce,true);
	thread td3(runOnce,true);
	thread td4(runOnce,false);
	thread td5(runOnce,true);

	td1.join();
	td2.join();
	td3.join();
	td4.join();
	td5.join();

	auto myPrint2 = [](int value)
	{
		cout << "hello call_once !!! value = " << value << endl;
	};

	for(int i = 0; i < 10; i++)
	{
		std::call_once(myFlag2,myPrint2,i);
	}

	return 0;
}

/* compile : g++ -std=c++11 -o Demo once_flag_call_once.cpp -pthread */
