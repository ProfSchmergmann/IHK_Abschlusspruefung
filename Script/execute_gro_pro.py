import subprocess
import sys

if __name__ == '__main__':
    INPUT_FOLDER_STRING = '-inputfolder'
    OUTPUT_FOLDER_STRING = '-outputfolder'
    LOG_STRING = '-log'
    outputfolder = 'output'
    inputfolder = 'input'
    logoption = 'true'

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
            i += 1

    subprocess.call(['java', '-jar', 'IHK_Abschlusspruefung.jar',
                     INPUT_FOLDER_STRING, inputfolder,
                     OUTPUT_FOLDER_STRING, outputfolder,
                     LOG_STRING, logoption])
