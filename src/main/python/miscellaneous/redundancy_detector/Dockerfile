# for now I didn't manage to build the image for 3.11+
FROM public.ecr.aws/lambda/python:3.9

# Install dependencies
RUN yum update -y && yum install -y \
    gcc-c++ \
    make \
    cmake3 \
    python3-devel \
    glibc-devel \
    ninja-build

RUN python3 -m pip install --upgrade pip && \
    python3 -m pip install \
    numpy \
    scipy \
    pillow \
    spacy \
    blis==0.7.9

# Pre-download required models
RUN python3 -m spacy download en_core_web_md
RUN python3 -m spacy download fr_core_news_md
RUN python3 -m spacy download es_core_news_md

COPY lambda_function.py ${LAMBDA_TASK_ROOT}
CMD ["lambda_function.lambda_handler"]
