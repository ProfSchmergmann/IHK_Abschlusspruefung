import os
import sys

# lcg_modes = ["ansi-c", "minimal-standard",
#              "randu", "simscript", "nag-lcg", "maple-lcg"]
# p_nop = ["", "-p"]
# number_amount = [10, 1000, 50000]
# execute_command_format = "java -jar GroPro.jar -analyse -m {} -n {} -o {} {}"

if __name__ == '__main__':
    os.system("java -jar IHK_Abschlusspruefung.jar")
    # if(len(sys.argv) != 2):
    #     print("Output directory has to be set")
    #     exit()

    # for number in number_amount:
    #     for lcg_mode in lcg_modes:
    #         for p_mode in p_nop:
    #             file_name = os.path.join(sys.argv[1], "{}{}_{}.txt".format(
    #                 lcg_mode, "_polar-method" if p_mode == "-p" else "", number))
    #             execute_command = execute_command_format.format(
    #                 lcg_mode, number, file_name, p_mode)
    #             print("Starting {}".format(execute_command))
    #             os.system(execute_command)
    #     #Timecoin
    #     file_name = os.path.join(sys.argv[1], "timecoin_{}.txt".format(number))
    #     execute_command = execute_command_format.format(
    #         lcg_mode, number, file_name, "-tc")
    #     print("Starting {}".format(execute_command))
    #     os.system(execute_command)
