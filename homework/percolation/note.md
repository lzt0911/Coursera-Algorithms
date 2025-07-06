# Percolation(渗透)(94/100)
![alt text](../../images/image-4.png)
* 思路：使用双节点`virtualTop`和`virtualBottom`
  ![alt text](../../images/image-5.png)
* 倒灌问题
  * 判断是否渗透直接通过判断`virtualTop`、`virtualBottom` 是否`connected()`。所以一旦渗透，所有与`virtualBottom`节点相连的节点也就都与`virtualTop`相连了。
