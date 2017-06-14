# BitmapConfig
Bitmap config ARGB_8888/RGB_565/ARGB_4444/ALPHA_8 sample

## 实践结果

### 运行截图
![screenshot][1]

### 结果统计

| bitmap.config | ALPHA_8 | ARGB_4444  | ARGB_8888| RGB_565|
| :-------------: |:-------------:|:-----:|:----:|:----:|
|![][2]png8   |**4**~~`1`~~ X|2 X|4 X|**4**~~`2`~~ X A ~~`不`~~ 透明|
|![][3]png24 |**4**~~`1`~~ X|2 X|4 X|**2** X|
|![][4]png32 |**4**~~`1`~~ X|2 X|4 X|**4**~~`2`~~ X A ~~`不`~~ 透明|
|![][5]jpeg    |**4**~~`1`~~ X|2 X|4 X|**2** X|

**请注意表格中带删除线的部分**

1. ALPHA_8：config占用的内存竟然和ARGB_8888一样，不是说每个像素占用1字节的么？
2. RGB_565：在png8和png32中，图片中的A都保持了50%的透明度，而且占用的内存也和ARGB_8888一样，不是说RGB_565不包含alpha么？不是说占用的内存是ARGB_8888的一半么？
3. ARGB_4444：在android 6.0上面，png8和png32看不见（全透明），png24和jpeg显示为一块黑色区域，在android 4.2上则显示正常。

[1]: https://raw.githubusercontent.com/Jamling/BitmapConfig/master/screenshot.jpg
[2]: https://raw.githubusercontent.com/Jamling/BitmapConfig/master/app/src/main/res/mipmap-xhdpi/png8.png
[3]: https://raw.githubusercontent.com/Jamling/BitmapConfig/master/app/src/main/res/mipmap-xhdpi/png24.png
[4]: https://raw.githubusercontent.com/Jamling/BitmapConfig/master/app/src/main/res/mipmap-xhdpi/png32.png
[5]: https://raw.githubusercontent.com/Jamling/BitmapConfig/master/app/src/main/res/mipmap-xhdpi/jpeg_80.jpg
