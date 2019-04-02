# coding=utf-8
# encoding = utf-8
import requests
import sys


def upToFir():
    # 打印传递过来的参数数组长度，便于校验
    print len(sys.argv)
    upUrl = sys.argv[1]
    appName = sys.argv[2].decode("gbk").encode("utf-8")
    bundleId = sys.argv[3]
    verName = sys.argv[4]
    apiToken = sys.argv[5]
    iconPath = sys.argv[6]
    apkPath = sys.argv[7]
    buildNumber = sys.argv[8]
    changeLog = sys.argv[9].decode("gbk").encode("utf-8")
    queryData = {'type': 'android', 'bundle_id': bundleId, 'api_token': apiToken}
    iconDict = {}
    binaryDict = {}
    # 获取上传信息
    try:
        response = requests.post(url=upUrl, data=queryData)
        json = response.json()
        iconDict = (json["cert"]["icon"])
        binaryDict = (json["cert"]["binary"])
    except Exception as e:
        print('query:' + e)

    # 上传apk
    try:
        file = {'file': open(apkPath, 'rb')}
        param = {"key": binaryDict['key'],
                 'token': binaryDict['token'],
                 "x:name": appName,
                 "x:version": verName,
                 "x:build": buildNumber,
                 "x:changelog": changeLog}
        req = requests.post(url=binaryDict['upload_url'], files=file, data=param, verify=False)
        print 'success_apk:' + req.content
    except Exception as e:
        print'error_apk:' + e

    # 上传logo
    try:
        file = {'file': open(iconPath, 'rb')}
        param = {"key": iconDict['key'],
                 'token': iconDict['token']}
        req = requests.post(url=iconDict['upload_url'], files=file, data=param, verify=False)
        print 'success_icon:' + req.content
    except Exception as e:
        print'error_icon:' + e


if __name__ == '__main__':
    upToFir()
