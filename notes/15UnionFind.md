# 1.5 Union Find(并查集)
* quick find
  * 相连接的元素拥有相同的编号，id数组存放元素的编号
  ```java
  public class QuickFindUF
  {
    private int[] id;
    
    public QuickFindUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    public boolean connected(int p, int q)
    {
        return id[p] == id[q];
    }

    public void union(int p, int q)
    {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++)
        {
            if (id[i] == pid)
                id[i] = qid;
        }
    }
  }
  ```
* quick union
  * 相连接的元素拥有共同的root，id数组存放元素的parent
  ```java
  public class QuickUnionUF
  {
    private int[] id;

    public QuickUnionUF(int N)
    {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    private int root(int i)
    {
        while (i != id[i])
            i = id[i];
        return i;
    }

    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }

    public void union(int p, int q)
    {
        int proot = root(p);
        int qroot = root(q);
        id[proot] = qroot;
    }
  }
  ```
  * 改进：跟踪每棵树的大小，使小树的root为大树
    ```java
    public void union(int p, int q)
    {
        // sz 存放每棵树的大小
        int proot = root(p);
        int qroot = root(q);
        if (proot == qroot)
            return;
        if (sz[proot] < sz[qroot]) 
        {
            id[proot] = qroot;
            sz[qroot] += sz[proot];
        } else {
            id[qroot] = proot;
            sz[proot] += sz[qroot];
        }
    }
    ```
  * 改进：路径压缩
    ```java
    private int root(int i)
    {
        while (i != id[i])
        {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }
    ```