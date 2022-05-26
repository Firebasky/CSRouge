# CSRouge
恶意的CS服务器,前几天看了一个文章大概是讲CS的一些漏洞,里面说到了反序列化漏洞。

自己为了好玩就单独实现了一下

**本质上只能urldns** 自己找了一下cs中的gadgets没有找到...

## 使用

java -Djavax.net.ssl.keyStore=./cobaltstrike.store -Djavax.net.ssl.keyStorePassword=123456 -jar CSRouge.jar 7777 http://dnslog.dnslog

客服端密码随便输入
