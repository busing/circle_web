#!/bin/sh
export LANG=en_US.UTF-8 
export LC_ALL=en_US.UTF-8

SETUP_DATE=`date "+%Y%m%d"`

#定义当前工作目录
WORK_PATH=`pwd`
BUILD_PATH=circle_web_build
SVN_NAME=circle_web

#SVN配置
SVN_PATH=http://code.taobao.org/svn/circleproject/%E5%9C%88%E5%AD%90%E9%A1%B9%E7%9B%AE%E7%AE%A1%E7%90%86/%E6%BA%90%E7%A0%81/truck/code/circle_web
SVN_USER=taiyuan321@163.com
SVN_PWD=2010comeon

#发布目录
PUBLISH_PATH=/home/circle/circle_web
UPLOADFILEBASEPATH=/home/circle/circle_images
#/home/circle/circle_web/uploadfiles
DOMAIN=http://comefarm.com/


build()
{
	#删除编译目录
	LogWarnMsg 'delete '$BUILD_PATH
	cd $WORK_PATH
	rm -rf $BUILD_PATH
	rm -rf resources

	#svn check 源码
	LogMsg 'check source from svn……'
	svn checkout $SVN_PATH --username $SVN_USER --password $SVN_PWD
	
	mv $SVN_NAME $BUILD_PATH
	cd $BUILD_PATH
	
	LogWarnMsg 'build class'
	ant -f build.xml -l buildlog.txt
	
	cd $WORK_PATH
	cp -rf $BUILD_PATH/build/resources ./
	LogSucMsg 'build success'
	
	#发布程序
	publish
	
	#重新启动tomcat
	restartTomcat
}


publish()
{
	rm -rf $PUBLISH_PATH
	if [ ! -d '$PUBLISH_PATH' ]; then
		mkdir $PUBLISH_PATH
	else
		BACKUP_DIR=$PUBLISH_PATH'_bak_'${SETUP_DATE}
		LogWarnMsg 'back up $PUBLISH_PATH to BACKUP_DIR'
		cp -rf $PUBLISH_PATH $BACKUP_DIR
	fi
	LogSucMsg 'publish to '$PUBLISH_PATH
	cp -rf resources/* $PUBLISH_PATH/
	sed -i 's%uploadFileBasePath=.*%uploadFileBasePath='$UPLOADFILEBASEPATH'%g' /home/circle/circle_web/WEB-INF/properties/config.properties
	sed -i 's%domain=.*%domain='$DOMAIN'%g' /home/circle/circle_web/WEB-INF/properties/config.properties
	rm -rf /home/circle/circle_web/uploadfiles
	ln -s -f $UPLOADFILEBASEPATH /home/circle/circle_web/uploadfiles
}


restartTomcat()
{
	LogWarnMsg "INFO: Cheking whether tomcat is running...."
    COUNT=`ps -ef | grep -v grep | grep -c tomcat`
    if [ ${COUNT} -ne 0 ]; then
        pid=`ps -ef | grep tomcat | grep -v grep | awk -F ' ' '{print $2}'`
        LogWarnMsg "INFO: tomcat is running, pid is ${pid}, killing"
        kill -9 ${pid}
    fi
	sh /home/circle/apache-tomcat-7.0.57/bin/startup.sh
	LogSucMsg 'start tomcat'
}

#日志函数

#colour level
SETCOLOR_SUCCESS="echo -en \\033[1;32m"  
SETCOLOR_FAILURE="echo -en \\033[1;31m"
SETCOLOR_WARNING="echo -en \\033[1;33m"
SETCOLOR_NORMAL="echo -en \\033[0;39m"

LogMsg()
{
	time=`date "+%D %T"`
	echo "[$time] : INFO    : $*"
	$SETCOLOR_NORMAL
}

LogWarnMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_WARNING
	echo "[$time] : WARN    : $*"
	$SETCOLOR_NORMAL
}

LogSucMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_SUCCESS
	echo "[$time] : SUCCESS : $*"
	$SETCOLOR_NORMAL
}

LogErrorMsg()
{
	time=`date "+%D %T"`
	$SETCOLOR_FAILURE
	echo "[$time] : ERROR   : $*"
	$SETCOLOR_NORMAL
}



build;








