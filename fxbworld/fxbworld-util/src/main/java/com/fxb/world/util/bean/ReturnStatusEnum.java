package com.fxb.world.util.bean;

import java.io.Serializable;

/**
 * 请求返回状态枚举...
 * 
 * @author pjw
 * 
 */
public enum ReturnStatusEnum implements Serializable{
	/**
	 * 200：成功; 服务器已成功处理了请求.
	 */
	OK(200, "成功"),
	/**
	 * 500：异常; 服务器请求异常.
	 */
	Error(500, "异常"),

	/***** begin业务检查异常枚举 *******/
	/**
	 * 301：业务检查异常：库存不足.
	 */
	StockNotEnough(301, "业务检查异常：库存不足"),
	/***** end 业务检查异常枚举 *******/

	/**
	 * 100：继续; 请求者应当继续提出请求。 服务器返回此代码表示已收到请求的第一部分，正在等待其余部分.
	 */
	Continue(100, "继续"),
	/**
	 * 101：切换协议; 请求者已要求服务器切换协议，服务器已确认并准备切换.
	 */
	SwitchingProtocols(101, "切换协议"),
	/**
	 * 201：已创建; 请求成功并且服务器创建了新的资源.
	 */
	Created(201, "已创建"),
	/**
	 * 400：错误请求; 服务器不理解请求的语法.
	 */
	BadRequest(400, "错误请求"),
	/**
	 * 401：未授权; 请求要求身份验证.
	 */
	Unauthorized(401, "未授权"),
	/**
	 * 403：禁止; 服务器拒绝请求.
	 */
	Forbidden(403, "服务器拒绝请求"),
	/**
	 * 404：未找到; 服务器找不到请求的网页.
	 */
	FileNotFound(404, "未找到"),
	/**
	 * 405：禁用的方法; 禁用请求中指定的方法.
	 */
	MethodNotAllowed(405, "禁用的方法");

	/**
	 * ....
	 * 
	 * @param status
	 *            返回状态编号...
	 * @param info
	 *            返回状态描述信息...
	 */
	ReturnStatusEnum(int status, String info) {
		this.setValue(status);
		this.setInfo(info);
	}

	/**
	 * 返回状态编号...
	 */
	private int value;
	/**
	 * 返回状态描述信息...
	 */
	private String info;

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
}

// HTTP 400 - 请求无效
// HTTP 401.1 - 未授权：登录失败
// HTTP 401.2 - 未授权：服务器配置问题导致登录失败
// HTTP 401.3 - ACL 禁止访问资源
// HTTP 401.4 - 未授权：授权被筛选器拒绝
// HTTP 401.5 - 未授权：ISAPI 或 CGI 授权失败
// HTTP 403 - 禁止访问
// HTTP 403 - 对 Internet 服务管理器 的访问仅限于 Localhost
// HTTP 403.1 禁止访问：禁止可执行访问
// HTTP 403.2 - 禁止访问：禁止读访问
// HTTP 403.3 - 禁止访问：禁止写访问
// HTTP 403.4 - 禁止访问：要求 SSL
// HTTP 403.5 - 禁止访问：要求 SSL 128
// HTTP 403.6 - 禁止访问：IP 地址被拒绝
// HTTP 403.7 - 禁止访问：要求客户证书
// HTTP 403.8 - 禁止访问：禁止站点访问
// HTTP 403.9 - 禁止访问：连接的用户过多
// HTTP 403.10 - 禁止访问：配置无效
// HTTP 403.11 - 禁止访问：密码更改
// HTTP 403.12 - 禁止访问：映射器拒绝访问
// HTTP 403.13 - 禁止访问：客户证书已被吊销
// HTTP 403.15 - 禁止访问：客户访问许可过多
// HTTP 403.16 - 禁止访问：客户证书不可信或者无效
// HTTP 403.17 - 禁止访问：客户证书已经到期或者尚未生效 HTTP 404.1 -
// 无法找到 Web 站点
// HTTP 404- 无法找到文件
// HTTP 405 - 资源被禁止
// HTTP 406 - 无法接受
// HTTP 407 - 要求代理身份验证
// HTTP 410 - 永远不可用
// HTTP 412 - 先决条件失败
// HTTP 414 - 请求 - URI 太长
// HTTP 500 - 内部服务器错误
// HTTP 500.100 - 内部服务器错误 - ASP 错误
// HTTP 500-11 服务器关闭
// HTTP 500-12 应用程序重新启动
// HTTP 500-13 - 服务器太忙
// HTTP 500-14 - 应用程序无效
// HTTP 500-15 - 不允许请求 global.asa
// Error 501 - 未实现
// HTTP 502 - 网关错误
// 用户试图通过 HTTP 或文件传输协议 (FTP) 访问一台正在运行 Internet 信息服务 (IIS) 的服务器上的内容时，IIS
// 返回一个表示该请求的状态的数字代码。该状态代码记录在 IIS 日志中，同时也可能在 Web 浏览器或 FTP
// 客户端显示。状态代码可以指明具体请求是否已成功，还可以揭示请求失败的确切原因。
// 日志文件的位置
// 在默认状态下，IIS 把它的日志文件放在 %WINDIRSystem32Logfiles 文件夹中。每个万维网 (WWW) 站点和 FTP
// 站点在该目录下都有一个单独的目录。在默认状态下，每天都会在这些目录下创建日志文件，并用日期给日志文件命名（例如，exYYMMDD.log）。
// HTTP
// 1xx - 信息提示
// 这些状态代码表示临时的响应。客户端在收到常规响应之前，应准备接收一个或多个 1xx 响应。 • 100 - 继续。
// • 101 - 切换协议。
// 2xx - 成功
// 这类状态代码表明服务器成功地接受了客户端请求。 • 200 - 确定。客户端请求已成功。
// • 201 - 已创建。
// • 202 - 已接受。
// • 203 - 非权威性信息。
// • 204 - 无内容。
// • 205 - 重置内容。
// • 206 - 部分内容。
// 3xx - 重定向
// 客户端浏览器必须采取更多操作来实现请求。例如，浏览器可能不得不请求服务器上的不同的页面，或通过代理服务器重复该请求。 • 302 - 对象已移动。
// • 304 - 未修改。
// • 307 - 临时重定向。
// 4xx - 客户端错误
// 发生错误，客户端似乎有问题。例如，客户端请求不存在的页面，客户端未提供有效的身份验证信息。 • 400 - 错误的请求。
// • 401 - 访问被拒绝。IIS 定义了许多不同的 401 错误，它们指明更为具体的错误原因。这些具体的错误代码在浏览器中显示，但不在 IIS
// 日志中显示： • 401.1 - 登录失败。
// • 401.2 - 服务器配置导致登录失败。
// • 401.3 - 由于 ACL 对资源的限制而未获得授权。
// • 401.4 - 筛选器授权失败。
// • 401.5 - ISAPI/CGI 应用程序授权失败。
// • 401.7 – 访问被 Web 服务器上的 URL 授权策略拒绝。这个错误代码为 IIS 6.0 所专用。
// • 403 - 禁止访问：IIS 定义了许多不同的 403 错误，它们指明更为具体的错误原因： • 403.1 - 执行访问被禁止。
// • 403.2 - 读访问被禁止。
// • 403.3 - 写访问被禁止。
// • 403.4 - 要求 SSL。
// • 403.5 - 要求 SSL 128。
// • 403.6 - IP 地址被拒绝。
// • 403.7 - 要求客户端证书。
// • 403.8 - 站点访问被拒绝。
// • 403.9 - 用户数过多。
// • 403.10 - 配置无效。
// • 403.11 - 密码更改。
// • 403.12 - 拒绝访问映射表。
// • 403.13 - 客户端证书被吊销。
// • 403.14 - 拒绝目录列表。
// • 403.15 - 超出客户端访问许可。
// • 403.16 - 客户端证书不受信任或无效。
// • 403.17 - 客户端证书已过期或尚未生效。
// • 403.18 - 在当前的应用程序池中不能执行所请求的 URL。这个错误代码为 IIS 6.0 所专用。
// • 403.19 - 不能为这个应用程序池中的客户端执行 CGI。这个错误代码为 IIS 6.0 所专用。
// • 403.20 - Passport 登录失败。这个错误代码为 IIS 6.0 所专用。
// • 404 - 未找到。 • 404.0 -（无） – 没有找到文件或目录。
// • 404.1 - 无法在所请求的端口上访问 Web 站点。
// • 404.2 - Web 服务扩展锁定策略阻止本请求。
// • 404.3 - MIME 映射策略阻止本请求。
// • 405 - 用来访问本页面的 HTTP 谓词不被允许（方法不被允许）
// • 406 - 客户端浏览器不接受所请求页面的 MIME 类型。
// • 407 - 要求进行代理身份验证。
// • 412 - 前提条件失败。
// • 413 – 请求实体太大。
// • 414 - 请求 URI 太长。
// • 415 – 不支持的媒体类型。
// • 416 – 所请求的范围无法满足。
// • 417 – 执行失败。
// • 423 – 锁定的错误。
// 5xx - 服务器错误

// 100：Continue
// 101：Switching Protocols
// 102：Processing
//
// 200：OK
// 201：Created
// 202：Accepted
// 203：Non-Authoriative Information
// 204：No Content
// 205：Reset Content
// 206：Partial Content
// 207：Multi-Status
//
// 300：Multiple Choices
// 301：Moved Permanently
// 302：Found
// 303：See Other
// 304：Not Modified
// 305：Use Proxy
// 306：(Unused)
// 307：Temporary Redirect
//
// 400：Bad Request
// 401：Unauthorized
// 402：Payment Granted
// 403：Forbidden
// 404：File Not Found
// 405：Method Not Allowed
// 406：Not Acceptable
// 407：Proxy Authentication Required
// 408：Request Time-out
// 409：Conflict
// 410：Gone
// 411：Length Required
// 412：Precondition Failed
// 413：Request Entity Too Large
// 414：Request-URI Too Large
// 415：Unsupported Media Type
// 416：Requested range not satisfiable
// 417：Expectation Failed
// 422：Unprocessable Entity
// 423：Locked
// 424：Failed Dependency
//
// 500：Internal Server Error
// 501：Not Implemented
// 502：Bad Gateway
// 503：Service Unavailable
// 504：Gateway Timeout
// 505：HTTP Version Not Supported
// 507：Insufficient Storage