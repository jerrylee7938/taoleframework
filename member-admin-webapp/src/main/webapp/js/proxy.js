function exec_main(id, param, address){
	if(typeof(exec_obj)=='undefined'){
		exec_obj = document.createElement('iframe');
		exec_obj.name = 'tmp_frame';
		exec_obj.src = 'http://portal.51taole.cn/portal/proxy.jsp?id='+id+"&param="+param+"&address="+address;
		exec_obj.style.display = 'none';
		document.body.appendChild(exec_obj);
	}else{
		exec_obj.src = 'http://portal.51taole.cn/portal/proxy.jsp?id='+id+"&param="+param+"&address="+address;
	}
}