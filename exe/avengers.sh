######### PARAM ######################################
APP_ENVIRONMENT="$3"
BASE_PATH="$2"
cd "${BASE_PATH}"
pwd;
JAVA_OPT=-Xmx2048m
JARFILE=`ls -1r *.jar 2>/dev/null | head -n 1`
PID_FILE=pid.file
RUNNING=N
PWD=`pwd`
RUN_FROM=`../../albo-biblioteca-comics/target/`

######### DO NOT MODIFY ########

if [ -f $PID_FILE ]; then
        PID=`cat $PID_FILE`
        if [ ! -z "$PID" ] && kill -0 $PID 2>/dev/null; then
                RUNNING=Y
        fi
fi

start()
{
        pwd;
        cd "${BASE_PATH}"
        if [ $RUNNING == "Y" ]; then
                echo "Application already started"
        else
                if [ -z "$JARFILE" ]
                then
                        echo "ERROR: jar file not found"
                else
						fuser -k -n tcp 80
                        cd "../../albo-biblioteca-comics/"
                        cd "target/"
                        nohup java  $JAVA_OPT "-Dspring.profiles.active=${APP_ENVIRONMENT}" -Djava.security.egd=file:/dev/./urandom  -Dserver.port=80-jar $JARFILE > nohup.out 2>&1  &
                        echo $! > $PID_FILE
                        echo "Application $JARFILE starting..."
                        tail -f nohup.out
                fi
        fi
}

stop()
{

        cd "${BASE_PATH}"
        if [ $RUNNING == "Y" ]; then
                kill -5 $PID
                rm -f $PID_FILE
                echo "Application stopped"
                RUNNING="N"
        else
                echo "Application not running"
        fi
}

restart()
{
        stop
        start
}

case "$1" in

        'start')
                start
                ;;

        'stop')
                stop
                ;;

        'restart')
                restart
                ;;

        *)
                echo "Usage: $0 {  start | stop | restart  }"
                exit 1
                ;;
esac
exit 0