# Multimedia-System-Reports
This is my homeworks and final project of Multimedia-System-Reports. Bezier Curve and Fractal is implemented by recursive way. The final project about **morphing** is implemented by following the algorithm from **Feture-Based Image Metamorphosis** which is proposed by Beir et al in 1992.

## Impelmentation
+ Java
+ GUI library : javaFx
+ Image library : opencv

## Enviroment
+ OS: Windows7
+ JRE: 1.8.0_65

## Bezier Curve
<table>
  <tr>
    <td><img height="300" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Bezier%20Curve%20result/1.png" /></td>     <td><img height="300" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Bezier%20Curve%20result/2.png" /></td>
  </tr>
</table>

## Fractal
<table>
  <tr>
    <td><img height="300" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Fractal%20result/1.png" /></td>     <td><img height="300" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Fractal%20result/2.png" /></td>
  </tr>
</table>

## Morphing
Morphing is an image processing technique typically used as an animation tool for metamorphosis from one image to another. Main step is as following,
### How does morphing do ?
1. Wrap first image into second image
2. Wrap second image into first
3. Let the first one fade out
4. Let the second one fade in 
### What is Morphing clearly?
Wrapping (shape) + Cross dissolving (color)
<p align="center">
<img height="200" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/1.png" />
</p>

### Algorithm
<p align="center">
<table>
  <tr align="center">
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/2.png" /></td>
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/3_1.png" /></td>
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/4.png" /></td>
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/5.png" /></td>
  </tr>
  <tr align="center">
    <td>1. Single line pair</td>
    <td>2. Calculate u</td>
    <td>3. Calculate v</td>
    <td>4. Use u and v to find  X’</td>
  </tr>
  <tr align="center">
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/6.png" /></td>
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/7.png" /></td>
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/8.png" /></td>
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/9.png" /></td>
  </tr>
  <tr align="center">
    <td>5. Multiple line pair</td>
    <td>6. Decide the weight  for each point</td>
    <td>7. Rotate the wrap line</td>
    <td>8. Calculate middle line</td>
  </tr>
  <tr align="center">
    <td><img height="100" src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/10.png" /></td>
  </tr>
  <tr align="center">
    <td>9. Use bilinear interpolation to find the x</td>
  </tr>
</table>
</p>

### Demo
<table>
  <tr align="center">
    <td><img src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/demo1.png" /></td>
    <td><img src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/demo2.png" /></td>
    <td><img src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing%20img/demo3.png" /></td>
  </tr>
  <tr align="center">
    <td>Pick two images</td>
    <td>Draw corresponding lines</td>
    <td>Create Morphing</td>
</table>

### Result
<p align="center">
  <img src="https://github.com/ChienKangLu/Multimedia-System-Reports/blob/master/Morphing/output/z.gif" />
</p>

## Reference
+ Multimedia system course of international management department in NTUST
+ [Feture-Based Image Metamorphosis](https://dl.acm.org/citation.cfm?id=134003)





