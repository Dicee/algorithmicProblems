import json
from typing import Dict, List, Any
import spacy
import re
import boto3
import csv
import io
import logging
import sys

logging.basicConfigs(stream=sys.stdout, level=logging.INFO)
logger = logging.getLogger('RedundancyDetector')

MODEL_NAMES = {
   "en": "en_core_web_md",
   "es": "es_core_news_md",
   "fr": "fr_core_news_md"
}

LM = {}

EXCLUDED_POS = {"ADP", "CCONJ", "CARDINAL", "SCONJ", "DET", "NUM"}
REDUNDANT_THRESHOLD = 2

s3 = boto3.client('s3')

def lambda_handler(event: Dict[str, Any], context: Any) -> Dict[str, Any]:
    records = event['Records']
    
    if len(records) > 1:
        raise Exception(f'Expected 1 record but got {len(records)}')

    record = records[0]['s3']
    bucket = record['bucket']['name']
    key = record['object']['key']

    try:
        results = ['record_id\tredundant_words']
        response = s3.get_object(Bucket=bucket, Key=key)
        content = response['Body'].read().decode('utf-8')

        tsv_file = csv.reader(io.StringIO(content), delimiter='\t', quoting=csv.QUOTE_NONE, escapechar='\\')
        next(tsv_file, None) # skip header

        for row in tsv_file:
            record_id, text, lang = row
            redundant_words = detect_redundant_words(text, lang)

            result_line = f'{record_id}\t{json.dumps(redundant_words)}'
            results.append(result_line)

       # Write results to S3
       output_key = f'redundancy_detector/{key}'
       output_content = '\n'.join(results)

       return {
               'statusCode': 200,
               'message': f'Processed{len(results)} items. Results written to s3://{bucket}/{output_key}'
               
       }
    except Exception as e:
        logger.error(f'Failed while processing s3://{bucket}/{key}')
        raise e

def detect_redundant_words(text, lang): 
    lemma_occurrences = dict()
    clean_text = re.sub(r'[\W]+', ' ', text)

    lm = get_model(lang)
    annotations = lm(clean_text.lower())

    pos_tags = [(token.text, token.pos_) for token in annotations]
    lemmas_to_text = {token.lemma_: token.text for token in annotations}

    for lemma, pos in zip(lemmas_to_text.keys(), pos_tags):
        if pos[1] not in EXCLUDED_POS:
            lemma_occurrences[lemma] = lemma_occurrences.get(lemma, 0) + 1

    return [lemma for lemma, occurrences in lemma_occurrences.items() if occurrences > REDUNDANT_THRESHOLD]

def get_model(lang):
    if not LM[lang]:
        logger.info(f'Model for {lang} has not been loaded yet. Loading now...')
        LM[lang] = spacy.load(MODEL_NAMES[lang])
        logger.info(f'Finished loading model for {lang}!')
    return LM[lang]

