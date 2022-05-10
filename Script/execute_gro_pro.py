import subprocess
import sys

if __name__ == '__main__':
    INPUT_FOLDER_STRING = '-inputfolder'
    inputfolder = 'input'
    OUTPUT_FOLDER_STRING = '-outputfolder'
    outputfolder = 'output'
    LOG_STRING = '-log'
    logoption = 'file'
    THREAD_POOL_SIZE_STRING = '-poolsize'
    threadpoolsize = 10
    SLEEP_TIME_STRING = '-sleep'
    sleeptime = 50

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
            i += 1

    subprocess.call(['java', '-jar', 'IHK_Abschlusspruefung.jar',
                     INPUT_FOLDER_STRING, inputfolder,
                     OUTPUT_FOLDER_STRING, outputfolder,
                     LOG_STRING, logoption,
                     THREAD_POOL_SIZE_STRING, threadpoolsize,
                     SLEEP_TIME_STRING, sleeptime])
