import os, sys, codecs

def absolute_path(root,subfile):
	return subfile if root == '.' else root + "/" + subfile

"""Fetch the list of the absolute path of all the files recursively contained by the given root"""
def fetch_files(root):
	if not os.path.isdir(root):
		return set({ root })
		
	files = { s for s in os.listdir(root) }
	return { x for y in files for x in fetch_files(absolute_path(root,y)) }
	
def add_package(root,file):
	reencoded_content = ""
	with codecs.open(file,"r","utf-8-sig") as fp:
		reencoded_content = fp.read()
	
	if len(reencoded_content) != 0:
		with codecs.open(file,"w","utf-8") as writer:
			last_slash_index = file.rfind("/")
			root_index       = file.find(root)

			writer.write("package " + file[root_index:last_slash_index].replace("/",".") + "\n\n")
			writer.write(reencoded_content)		
	
"""Print the usage of the command to the user"""
def print_usage():
	print("\n--------------\n     USAGE\n--------------")
	print("\nSyntax : unbom_utf8 <path>")
	
"""Fail and exit with a message"""
def fail(msg):
	print("Failed :",msg)
	print_usage()
	exit()
	
if __name__ == '__main__':
	if len(sys.argv) < 2:
		fail("Not enough arguments.")
		
	root  = os.getcwd().replace("\\","/") if sys.argv[1] == '.' else sys.argv[1]	 
	files = fetch_files(root)
	for file in files:
		add_package(root,file)