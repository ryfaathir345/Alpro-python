import heapq
from collections import defaultdict

# ===================== HUFFMAN =====================
class NodeH:
    def __init__(self, char, freq):
        self.char = char
        self.freq = freq
        self.left = None
        self.right = None
    def __lt__(self, other):
        return self.freq < other.freq

def build_huffman_tree(text):
    freq = defaultdict(int)
    for char in text:
        freq[char] += 1
    heap = [NodeH(char, f) for char, f in freq.items()]
    heapq.heapify(heap)
    while len(heap) > 1:
        left = heapq.heappop(heap)
        right = heapq.heappop(heap)
        merged = NodeH(None, left.freq + right.freq)
        merged.left = left
        merged.right = right
        heapq.heappush(heap, merged)
    return heap[0]

def generate_codes(node, code="", codes={}):
    if node:
        if node.char is not None:
            codes[node.char] = code
        generate_codes(node.left, code + "0", codes)
        generate_codes(node.right, code + "1", codes)
    return codes

def huffman_decoding(encoded_text, root):
    decoded = ""
    current = root
    for bit in encoded_text:
        current = current.left if bit == "0" else current.right
        if current.char is not None:
            decoded += current.char
            current = root
    return decoded

def huffman_menu():
    print("\n=== HUFFMAN CODING ===")
    text = input("Masukkan teks: ")
    root = build_huffman_tree(text)
    codes = generate_codes(root)
    encoded = "".join(codes[char] for char in text)

    print("\nKode Huffman:")
    for char, code in codes.items():
        print(f"{char}: {code}")

    print("\nHasil Encoding:")
    print(encoded)

    decoded = huffman_decoding(encoded, root)
    print("\nHasil Decoding:")
    print(decoded)

# ===================== DIJKSTRA =====================
def dijkstra(graph, start):
    distances = {node: float("inf") for node in graph}
    previous = {node: None for node in graph}
    distances[start] = 0
    pq = [(0, start)]
    while pq:
        curr_dist, curr_node = heapq.heappop(pq)
        for neighbor, weight in graph[curr_node]:
            distance = curr_dist + weight
            if distance < distances[neighbor]:
                distances[neighbor] = distance
                previous[neighbor] = curr_node
                heapq.heappush(pq, (distance, neighbor))
    return distances, previous

def get_path(previous, start, end):
    path = []
    current = end
    while current:
        path.append(current)
        current = previous[current]
    path.reverse()
    return path if path[0] == start else []

def dijkstra_menu():
    print("\n=== ALGORITMA DIJKSTRA ===")
    graph = defaultdict(list)
    edges = int(input("Jumlah edge: "))
    for _ in range(edges):
        u = input("Node asal: ")
        v = input("Node tujuan: ")
        w = int(input("Bobot: "))
        graph[u].append((v, w))
        graph[v].append((u, w))  # graf tidak berarah
    start = input("Node awal: ")
    if start not in graph:
        print("Node awal tidak ada di graph!")
        return
    distances, previous = dijkstra(graph, start)
    print("\nJarak dan Jalur Terpendek:")
    for node in distances:
        path = get_path(previous, start, node)
        path_display = " -> ".join(path) if path else "-"
        dist_display = distances[node] if distances[node] != float("inf") else "inf"
        print(f"{start} → {node} = {dist_display} | Jalur: {path_display}")

# ===================== MENU UTAMA =====================
while True:
    print("\n=== PROJECT UAS ALGORITMA PEMROGRAMAN ===")
    print("1. Huffman Coding (Encode & Decode)")
    print("2. Algoritma Dijkstra (Jarak & Jalur)")
    print("3. Keluar")
    choice = input("Pilih menu: ")
    if choice == "1":
        huffman_menu()
    elif choice == "2":
        dijkstra_menu()
    elif choice == "3":
        print("Program selesai.")
        break
    else:
        print("Pilihan tidak valid!")
