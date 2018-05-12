import math

def cproduct_vectors(a,b):
    ax = a[0]
    ay = a[1]
    az = a[2]
    bx = b[0]
    by = b[1]
    bz = b[2]
    cs = (ay*bz - az*by, -(ax*bz - az*bx), ax*by - ay*bx)
    return cs

def crossproduct_points(a,b,c,d):
    p = (a[0]-b[0], a[1]-b[1], a[2]-b[2])
    q = (c[0]-d[0], c[1]-d[1], c[2]-d[2])
    cs = cproduct_vectors(p,q)
    return cs


def get_normal(P, q, r):
    Q = (q[0]-P[0], q[1]-P[1], q[2]-P[2])
    R = (r[0]-P[0], r[1]-P[1], r[2]-P[2])
    n = (Q[1]*R[2] - Q[2]*R[1],
         -(Q[0]*R[2] - Q[2]*R[0]),
         Q[0]*R[1] - Q[1]*R[0])
    nLength = math.sqrt(n[0]*n[0] + n[1]*n[1] + n[2]*n[2])
    N = (n[0]/nLength, n[1]/nLength, n[2]/nLength)
    return N

p1 = (0,0,0)
p2 = (100,0,0)
p3 = (0,0,100)
p4 = (100,0,100)

N = get_normal(p1,p2,p3)

print "solid mysolid"
print "  facet normal ",N[0],N[1],N[2]
print "    outer loop"
print "      vertex",p1[0],p1[1],p1[2]
print "      vertex",p2[0],p2[1],p2[2]
print "      vertex",p3[0],p3[1],p3[2]
print "    endloop"
print "  endfacet"
print "  facet normal ",N[0],N[1],N[2]
print "    outer loop"
print "      vertex",p2[0],p2[1],p2[2]
print "      vertex",p3[0],p3[1],p3[2]
print "      vertex",p4[0],p4[1],p4[2]
print "    endloop"
print "  endfacet"
print "endsolid"