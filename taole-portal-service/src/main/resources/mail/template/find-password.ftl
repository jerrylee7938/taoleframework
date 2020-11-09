<div>
	<h3>亲爱的用户：</h3>
	您好！<br />
	<p>
		您在${date?string("yyyy年MM月dd日 HH:mm:ss")}提交找回密码请求，请点击下面的链接修改密码。<br />
		<a href="${hostUrl}/reset-password.jsp?token=${token}">${hostUrl}/reset-password.jsp?token=${token}</a> <br />
		(如果您无法点击这个链接，请将此链接复制到浏览器地址栏后访问)
		为了保证您帐号的安全性，该链接有效期为24小时，并且点击一次后将失效!
	</p>
	<p>
		如果您误收到此电子邮件，则可能是其他用户在尝试帐号设置时的误操作，如果您并未发起该请求，则无需再进行任何操作，并可以放心地忽略此电子邮件。
	</p>
	<p>
		感谢您使用北京一生佳系统！
	</p>
	<p>
		${date?string("yyyy年MM月dd日")}
	</p>
</div>