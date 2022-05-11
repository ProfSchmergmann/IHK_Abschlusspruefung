import subprocess
import sys

if __name__ == '__main__':
    INPUT_FOLDER_STRING = '-inputfolder'
    inputfolder = 'input'
    OUTPUT_FOLDER_STRING = '-outputfolder'
    outputfolder = 'output'
    LOG_STRING = '-log'
    logoption = 'file'
    LOG_LEVEL_STRING = "-loglvl"
    loglvl = 'all'
    THREAD_POOL_SIZE_STRING = '-poolsize'
    threadpoolsize = '1'
    SLEEP_TIME_STRING = '-sleep'
    sleeptime = '50'
    QUEUE_SIZE_STRING = '-queuesize'
    queueSize = '1'
    MASTER_WORKER_STRING = '-mw'
    masterWorker = 'false'

    if len(sys.argv) > 0:
        i = 0
        while i < len(sys.argv):
            if i < len(sys.argv) - 1:
                if sys.argv[i] == INPUT_FOLDER_STRING:
                    inputfolder = sys.argv[i + 1]
                    i += 1
                elif sys.argv[i] == OUTPUT_FOLDER_STRING:
                    outputfolder = sys.argv[i + 1]
                    i += 1
                elif sys.argv[i] == LOG_STRING:
                    logoption = sys.argv[i + 1]
                    i += 1
                elif sys.argv[i] == THREAD_POOL_SIZE_STRING:
                    threadpoolsize = sys.argv[i + 1]
                    i += 1
                elif sys.argv[i] == SLEEP_TIME_STRING:
                    sleeptime = sys.argv[i + 1]
                    i += 1
                elif sys.argv[i] == QUEUE_SIZE_STRING:
                    queueSize = sys.argv[i + 1]
                    i += 1
                elif sys.argv[i] == LOG_LEVEL_STRING:
                    loglvl = sys.argv[i + 1]
                    i += 1
                elif sys.argv[i] == MASTER_WORKER_STRING:
                    masterWorker = sys.argv[i + 1]
                    i += 1
            i += 1

    subprocess.call(['java', '-jar', 'IHK_Abschlusspruefung.jar',
                     INPUT_FOLDER_STRING, inputfolder,
                     OUTPUT_FOLDER_STRING, outputfolder,
                     LOG_STRING, logoption,
                     THREAD_POOL_SIZE_STRING, threadpoolsize,
                     SLEEP_TIME_STRING, sleeptime,
                     QUEUE_SIZE_STRING, queueSize,
                     LOG_LEVEL_STRING, loglvl,
                     MASTER_WORKER_STRING, masterWorker])
