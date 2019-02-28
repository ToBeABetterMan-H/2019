#pragma once 

#include <iostream>
#include <list>
#include <vector>
#include <boost/make_shared.hpp>

using boost::shared_ptr;

#define MAX_MODULE_SIZE 512
#define STATE_STOP      0
#define STATE_INITING   1   /* module 正在初始化 */
#define STATE_INIT      2   /* module 初始化完成 */
#define STATE_READYING  3
#define STATE_READY     4
#define STATE_RUNING    5
#define STATE_RUN       6

enum ModuleLevel
{
    Core_Hight   = 3 , 
    Core_Middle  = 6 , 
    Core_Normal  = 9 ,
    Core_Low     = 12,
    Hight        = 15,
    Middle       = 18,
    Normal       = 21,
    Low          = 24,
};

class Module
{
public:
    virtual bool init(boost::shared_ptr<Module> mod,int argc,char** argv) = 0;
    virtual void ready(boost::shared_ptr<Module> mod) = 0;
    virtual void run(boost::shared_ptr<Module> mod) = 0;
    virtual void stop(boost::shared_ptr<Module> mod) = 0;

    std::string getName();
    std::vector<boost::shared_ptr<Module>> getDepends();
    std::vector<boost::shared_ptr<Module>> getChildren();
    int getPriority();
    int getState();


    void setName(const std::string name);
    void setPriority(int priority);
    void setState(int state);

protected:
    std::string m_sName;                /* 模块名 */
    std::vector<boost::shared_ptr<Module>> m_vDepends;  /* 依赖模块 */
    std::vector<boost::shared_ptr<Module>> m_vChildren;  /* 子模块 */
    int m_iPriority;                    /* 优先级 */
    int m_iState;                       /* 状态 */
};

/* 单实例 */
class ModuleManage
{
public:
    static boost::shared_ptr<ModuleManage> getInstance()    /* 全局访问实例 */
    {
        static boost::shared_ptr<ModuleManage> manager = boost::shared_ptr<ModuleManage>( new ModuleManage() );
        return manager;
    }

    void Module_Regist(boost::shared_ptr<Module> mod);
    bool Module_Init(int argc,char** argv);
    void Module_Ready();
    void Module_Run();
    void Module_Stop();
    int  Module_GetState();
private:
    bool iniModule(const boost::shared_ptr<Module> mod,int argc,char** argv); /* 初始化 module */
    void readyModule(const boost::shared_ptr<Module> mod);
    void runModule(const boost::shared_ptr<Module> mod);
    void stopModule(const boost::shared_ptr<Module> mod);
    ModuleManage()
    {
        m_iSize = 0;
        m_iState = STATE_STOP;
    };
    ModuleManage(ModuleManage const &manager);
    ModuleManage& operator = (ModuleManage const &manager);

private:
    int m_iState;
    std::vector<boost::shared_ptr<Module>> m_vModule;
    int m_iSize;
};



/* 属性 __attribute__(constructor) 在 main 函数前运行 */
#define MODULE_AUTO_REGIST(fun) static void fun() __attribute__((constructor));\
void fun()
