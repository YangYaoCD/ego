上传文件绕过zuul，有两种方法：  
  *地址前+zuul  
  *nginx改地址，然后不经过zuul，直接访问uploadService(同时还要解决跨域问题)