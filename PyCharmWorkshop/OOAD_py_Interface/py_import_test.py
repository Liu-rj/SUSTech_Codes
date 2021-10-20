import tkinter.filedialog
import shutil
import importlib
import sys
import os
import argparse


def main(id):
    # 选择文件
    file = tkinter.filedialog.askopenfilename()
    # 获取当前脚本工作路径
    save_dir = os.path.join(os.getcwd(), 'user_scripts')
    sys.path.append(save_dir)
    # 提取目标文件路径和文件名
    file_path, file_name = os.path.split(file)
    # 移动目标文件到目标路径
    shutil.move(file, save_dir)
    save_file = os.path.join(save_dir, str(id) + '.py')
    # 修改文件名为规范化的文件名，通常为 'SID.py'
    if os.path.isfile(save_file):
        os.remove(save_file)
    os.rename(os.path.join(save_dir, file_name), save_file)
    # 获取目标文件里的目标函数
    module = importlib.import_module(str(id))
    constructor = module.add
    algo = constructor(2, 4, id)
    algo.add_self()
    save_name = str(id) + '.txt'
    txt_file = os.path.join(os.getcwd(), save_name)
    if os.path.isfile(txt_file):
        os.remove(txt_file)
    with open(save_name, 'w') as f:
        for arr in algo.answer:
            flag = False
            for ele in arr:
                if flag:
                    f.write(" " + str(ele))
                else:
                    flag = True
                    f.write(str(ele))
            f.write('\n')
    f.close()


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument(
        '--id',
        type=int,
        help='user id',
        default=11910000)
    args = parser.parse_args()
    main(args.id)
