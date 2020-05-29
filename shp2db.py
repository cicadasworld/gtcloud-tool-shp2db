#!/usr/bin/env python
# -*- coding: utf-8 -*-
__author__ = 'jin'


from argparse import ArgumentParser
import subprocess

def get_arguments():
    parser = ArgumentParser()
    parser.add_argument('-m', '--mapserver_ip', help='服务IP地址', required=True)
    parser.add_argument('-f', '--file', help='shp文件所在目录名', required=True)
    parser.add_argument('-p', '--prefix', help='shp数据中fid的前缀', required=True)
    arguments = parser.parse_args()
    return arguments

def main():
    # 解析命令行参数
    args = get_arguments()
    mapserver_ip = args.mapserver_ip
    file = args.file
    prefix = args.prefix
    jre_home = 'jre1.8.0_92\\bin\\java.exe'
    spring_boot_jar = 'libs\\gtcloud-tool-shp2db-1.0.0.jar'

    # 调用 java -jar gtcloud-tool-shp2db-1.0.0-SNAPSHOT.jar
    cmd_line_template = '{jre_home} -Xms512m -Xmx2048m -jar {spring_boot_jar} --server.endpoint=http://{mapserver_ip}:9111/camp-coordinate/ --shp.file.path={file} -shp.fid.prefix={prefix}'
    cmd_line = cmd_line_template.format(jre_home=jre_home, mapserver_ip=mapserver_ip, file=file, spring_boot_jar=spring_boot_jar, prefix=prefix)
    print(cmd_line)
    subprocess.run(cmd_line)


if __name__ == '__main__':
    main()