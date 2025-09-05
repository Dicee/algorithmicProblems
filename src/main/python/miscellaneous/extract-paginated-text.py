#!/home/courtino/.py3/bin/python3

from itertools import groupby
from PIL import Image

import os
import pytesseract

def is_image(file_name):
    _, extension = os.path.splitext(file_name)
    return extension in ['.jpg', '.png', '.bmp']

def get_base_name(file_name):
    extensionless, _  = os.path.splitext(file_name)
    last_dash_index = extensionless.rfind('-')
    if last_dash_index == -1:
        return extensionless

    last_name_part = extensionless[last_dash_index + 1:]
    if not last_name_part.isdigit():
        return extensionless
    return extensionless[:last_dash_index]

images = sorted([f for f in os.listdir() if is_image(f)])
grouped_images = { k: [v for v in values] for k, values in groupby(images, get_base_name) }

for group_name, group_files in grouped_images.items():
    is_single_page = len(group_files) == 1
    print(f'Processing group {group_name} with {len(group_files)} file{'' if is_single_page else 's'}...')
    transcript_file = group_name + '.txt'

    with open(transcript_file, 'w') as f:
        page = 1

        for group_file in group_files:
            print(f'Processing page {page} of {len(group_files)}')
            if not is_single_page:
                f.write(f'=========================== Page {page} ===========================\n\n')

            f.write(pytesseract.image_to_string(group_file))
            page += 1    
