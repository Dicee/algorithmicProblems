import sys
import shutil
import re
import os
from subprocess import call

"""Load a property file in a dictionary"""
def load_properties(path):
	res = {}
	with open(path,"r") as lines:
		for line in lines:
			split                 = line.split("=")
			res[split[0].strip()] = split[1].strip()
	return res
			
"""Load a property file in a dictionary"""
def print_properties(props,path):
	with open(path,"w") as writer:
		for k,v in props.items():
			writer.write("{}={}\n".format(k,v))
			
"""Interpret the regular expressions inputted by the user and convert them into Python regular expressions"""
def read_regexps(args,root):
	def get_pattern(file):
		if os.path.isdir(file):
			start = file.count(os.sep)
			# determine the depth of the directory
			depth = start + 1
			for top, dirs, files in os.walk(file):
				depth = max(top.count(os.sep) - start + 1,depth)

			patterns = ["/*"]
			for i in range(1,depth):
				patterns.append(patterns[i-1] + "/*")
			return [ file + x for x in patterns ]
		else:
			return [ file ]
	
	def get_regex(s):
		prefix = "" if root == "." else root + "/"
		return "^" + prefix + s.replace(".","\.").replace("*","[^/]+") + "$"
			
	return [ re.compile(get_regex(item)) for arg in args for item in get_pattern(arg) ]

"""Determine whether a file should be ignored or not"""
def accept(name,ignore):
	return name not in ignore
	
def absolute_path(root,subfile):
	return subfile if root == '.' else root + "/" + subfile
	
"""Fetch the list of the absolute path of all the files recursively contained by the given root"""
def fetch_files(ignore,root):
	if not os.path.isdir(root):
		return frozenset({ root }) if accept(root,ignore) else frozenset()
		
	files = { s for s in os.listdir(root) if accept(s,ignore) }
	return { x for y in files for x in fetch_files(ignore,absolute_path(root,y)) }
	
"""Return the list of all the files which path matches one of the given regular expressions"""
def filter_files(regs,files):
	matches = []
	for reg in regs:
		matches += [ file for file in files if reg.match(file) ]
		
	if matches:
		print(len(matches),"match(es)")
	else:
		print("Found no file matching any of these regular expressions")
	return matches
	
"""Copy all the specified files from from_dir to to_dir. If needed, the appropriate files and folders will be created."""
def copy_files(files,from_dir,to_dir):
	# make a backup copy, because I'm cautious : even MY code may contain bugs
	if not(os.path.exists(to_dir + "/bak")):
		os.mkdir(to_dir + "/bak")
		
	current = os.getcwd()
	os.chdir(to_dir)
	call([ "jar","-cf","bak/backup.jar" ] + files)
	os.chdir(current)
	
	# finally copy the files to the destination
	for file in files:
		path = to_dir + file[len(from_dir):]
		dirs = path[:path.rfind("/")]
		if not os.path.exists(dirs):
			os.makedirs(dirs)
		shutil.copyfile(file,path)
		print("copied :",file[len(from_dir) + 1:])
	
"""Print the usage of the command to the user"""
def print_usage():
	print("\n--------------\n     USAGE\n--------------")
	print("\nThis command enables to export (resp. import) files matching a regex to (resp. from) a given project from (resp. to) a git repository.")
	print("\nSyntax : update <operation> regex*")
	print("\n\nArgument 'operation' can be any of the following : \n  . export\n  . import\n  . ignore\n  . unignore")
	
"""Fail and exit with a message"""
def fail(msg):
	print("Failed :",msg)
	print_usage()
	exit()
	
if __name__ == '__main__':
	if len(sys.argv) < 3:
		fail("Not enough arguments.")
	
	props = load_properties("update.ini")
	
	if sys.argv[1] in [ "export","import" ]:
		ends = (props["eclipse-project"],props["repository"])
		if sys.argv[1] == "export":
			ends = (ends[1],ends[0])
		home = ends[0]
	elif sys.argv[1] in [ "ignore","unignore" ]:
		home = "."
	else:
		fail(sys.argv[1] + " is not a valid operation")
		
	local_ignore = set(props["ignore"].strip().split(";")).difference([""])
	ignore       = { home + "/" + x for x in local_ignore }
	regs         = read_regexps([ f for f in os.listdir(home) if accept(f,ignore) ],home) if sys.argv[2] == "-all" else read_regexps(sys.argv[2:],home)
	files        = fetch_files(ignore,home)
	selected     = filter_files(regs,files)

	if selected:
		if sys.argv[1] in [ "ignore","unignore" ]:
			props["ignore"] = ";".join(local_ignore.union(selected) if sys.argv[1] == "ignore" else local_ignore.difference(selected))
			print_properties(props,"update.ini")
		else:
			copy_files(selected,ends[0],ends[1])