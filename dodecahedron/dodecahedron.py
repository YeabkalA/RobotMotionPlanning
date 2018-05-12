import math 

faces = {0: [(0.0, 0.0, 1.0), (0.6666666666666666, 0.0, 0.7453559924999299), (0.7453559924999299,
0.5773502691896258, 0.3333333333333333), (0.12732200375003505, 0.9341723589627158,
0.3333333333333333), (-0.3333333333333333, 0.5773502691896257, 0.7453559924999299)], 1: [(0.0, 0.0,
1.0), (-0.3333333333333333, 0.5773502691896257, 0.7453559924999299), (-0.8726779962499649,
0.3568220897730899, 0.3333333333333333), (-0.8726779962499649, -0.35682208977308993,
0.3333333333333333), (-0.33333333333333326, -0.5773502691896257, 0.7453559924999299)], 2: [(0.0,
0.0, 1.0), (-0.33333333333333326, -0.5773502691896257, 0.7453559924999299), (0.1273220037500351,
-0.9341723589627157, 0.3333333333333333), (0.7453559924999299, -0.5773502691896258,
0.3333333333333333), (0.6666666666666666, 0.0, 0.7453559924999299)], 3: [(0.6666666666666666, 0.0,
0.7453559924999299), (0.7453559924999299, -0.5773502691896258, 0.3333333333333333),
(0.8726779962499649, -0.3568220897730899, -0.3333333333333333), (0.8726779962499649,
0.35682208977308993, -0.3333333333333333), (0.7453559924999299, 0.5773502691896258,
0.3333333333333333)], 4: [(-0.3333333333333333, 0.5773502691896257, 0.7453559924999299),
(0.12732200375003505, 0.9341723589627158, 0.3333333333333333), (-0.1273220037500351,
0.9341723589627157, -0.3333333333333333), (-0.7453559924999299, 0.5773502691896258,
-0.3333333333333333), (-0.8726779962499649, 0.3568220897730899, 0.3333333333333333)], 5:
[(-0.33333333333333326, -0.5773502691896257, 0.7453559924999299), (-0.8726779962499649,
-0.35682208977308993, 0.3333333333333333), (-0.7453559924999299, -0.5773502691896258,
-0.3333333333333333), (-0.12732200375003505, -0.9341723589627158, -0.3333333333333333),
(0.1273220037500351, -0.9341723589627157, 0.3333333333333333)], 6: [(0.3333333333333333,
-0.5773502691896257, -0.7453559924999299), (-0.12732200375003505, -0.9341723589627158,
-0.3333333333333333), (-0.7453559924999299, -0.5773502691896258, -0.3333333333333333),
(-0.6666666666666666, -0.0, -0.7453559924999299), (-0.0, -0.0, -1.0)], 7: [(0.33333333333333326,
0.5773502691896257, -0.7453559924999299), (0.8726779962499649, 0.35682208977308993,
-0.3333333333333333), (0.8726779962499649, -0.3568220897730899, -0.3333333333333333),
(0.3333333333333333, -0.5773502691896257, -0.7453559924999299), (-0.0, -0.0, -1.0)], 8:
[(-0.6666666666666666, -0.0, -0.7453559924999299), (-0.7453559924999299, 0.5773502691896258,
-0.3333333333333333), (-0.1273220037500351, 0.9341723589627157, -0.3333333333333333),
(0.33333333333333326, 0.5773502691896257, -0.7453559924999299), (-0.0, -0.0, -1.0)], 9:
[(-0.7453559924999299, -0.5773502691896258, -0.3333333333333333), (-0.8726779962499649,
-0.35682208977308993, 0.3333333333333333), (-0.8726779962499649, 0.3568220897730899,
0.3333333333333333), (-0.7453559924999299, 0.5773502691896258, -0.3333333333333333),
(-0.6666666666666666, -0.0, -0.7453559924999299)], 10: [(0.8726779962499649, -0.3568220897730899,
-0.3333333333333333), (0.7453559924999299, -0.5773502691896258, 0.3333333333333333),
(0.1273220037500351, -0.9341723589627157, 0.3333333333333333), (-0.12732200375003505,
-0.9341723589627158, -0.3333333333333333), (0.3333333333333333, -0.5773502691896257,
-0.7453559924999299)], 11: [(-0.1273220037500351, 0.9341723589627157, -0.3333333333333333),
(0.12732200375003505, 0.9341723589627158, 0.3333333333333333), (0.7453559924999299,
0.5773502691896258, 0.3333333333333333), (0.8726779962499649, 0.35682208977308993,
-0.3333333333333333), (0.33333333333333326, 0.5773502691896257, -0.7453559924999299)]}


graph = {0: [2, 3, 11, 4, 1], 1: [0, 4, 9, 5, 2], 2: [1, 5, 10, 3, 0], 3: [2, 10, 7, 11, 0], 4: [0, 11, 8,
9, 1], 5: [1, 9, 6, 10, 2], 6: [10, 5, 9, 8, 7], 7: [11, 3, 10, 6, 8], 8: [9, 4, 11, 7, 6], 9: [5,
1, 4, 8, 6], 10: [3, 2, 5, 6, 7], 11: [4, 0, 3, 7, 8]}


# Find the point at the center of a face
# Given a face number 0-11
def face_center(n):
    cx, cy, cz = 0, 0, 0
    for p in faces[n]:
        cx += p[0]
        cy += p[1]
        cz += p[2]
    return (cx/5, cy/5, cz/5)
    


# For adjacent faces numbered m and n,
# returns the point at the center of their common edge
def edge_center(m, n):
    edge = list(set(faces[m]) & set(faces[n]))
    return ( (edge[0][0] + edge[1][0])/2, (edge[0][1] + edge[1][1])/2, (edge[0][2] + edge[1][2])/2 )

def edge_partitions(m, n):
    partitions = []
    edge = list(set(faces[m]) & set(faces[n]))
    i = 0.1
    while i<1:
        partitions.append(( (edge[0][0] + edge[1][0])*i, (edge[0][1] + edge[1][1])*i, (edge[0][2] + edge[1][2])*i ))
        i = i + 0.1
    return partitions


def get_normal_for_face(n):
    points = [faces[n][0], faces[n][1], faces[n][2]]
    N = get_normal(points[0], points[1], points[2])
    return N


def get_normal(P, q, r):
    Q = (q[0]-P[0], q[1]-P[1], q[2]-P[2])
    R = (r[0]-P[0], r[1]-P[1], r[2]-P[2])
    n = (Q[1]*R[2] - Q[2]*R[1],
         -(Q[0]*R[2] - Q[2]*R[0]),
         Q[0]*R[1] - Q[1]*R[0])
    nLength = math.sqrt(n[0]*n[0] + n[1]*n[1] + n[2]*n[2])
    N = (n[0]/nLength, n[1]/nLength, n[2]/nLength)
    return N

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




# Builds a path, starting at face 0 -> face 1, 
# then following the given list, which contains indices of counter-clockwise turns.
def follow_path(turns):
    face_list = [0, 1]
    for t in turns:
        in_idx = graph[face_list[-1]].index(face_list[-2])
        out_idx = (in_idx + t) % 5
        face_list.append(graph[face_list[-1]][out_idx])
    return face_list

def stl_rect(p1,p2,p3,p4):
    N = get_normal(p1,p2,p3)
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

def generate_STL(face_list):
    print "solid layers"
    for i in range(len(face_list)-1):
        n = get_normal_for_face(face_list[i])
        N = (n[0]/2, n[1]/2, n[2]/2)
        p1 = face_center(face_list[i])
        p2 = edge_center(face_list[i],face_list[i+1])
        
        p3 = N + p1
        p4 = N + p2
        stl_rect(p1,p2,p3,p4)
        p1 = face_center(face_list[i+1])
        p2 = edge_center(face_list[i],face_list[i+1])
        p3 = N + p1
        p4 = N + p2
        stl_rect(p1,p2,p3,p4)

fl = follow_path([4, 2, 1, 3, 2, 3])
generate_STL(fl)





