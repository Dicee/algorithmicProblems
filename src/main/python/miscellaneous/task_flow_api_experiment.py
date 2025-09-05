import json
from inspect import signature

"""
This file is a small experiment, as I got curious of how Airflow managed to build a DAG so easily from the TaskFlow API, and I wanted to see how easy it would be to do it myself. Turns out, it's quite easy! Of course, I've only implemented the basics of basics, the full thing must be a bit more complex but at least I got to understand how to use Python decorators to do such things. Pretty powerful stuff!
"""
def task(multiple_outputs=False):
    def decorator(do_task):
        return Task(do_task, multiple_outputs)
    return decorator

class Task:
    def __init__(self, do_task, multiple_outputs):
        self.do_task = do_task
        self.multiple_outputs = multiple_outputs
        self.name = do_task.__name__
        self.dependencies = []
        self.xcom_dependencies = []

    def __call__(self, *args, **kwargs):
        if (len(kwargs) > 0):
            raise Error('kwargs not supported in this small demo')


        params = [param for param in signature(self.do_task).parameters.keys()]
        actual_args = []
        i = 0

        for arg in args:
            if isinstance(arg, TaskResult):
                xcom_dependency = params[i] if arg.task.multiple_outputs else 'return_value'

                actual_args.append(arg.results[xcom_dependency])
                self.xcom_dependencies.append((arg.task.name, xcom_dependency))
                self.dependencies.append(arg.task.name)    
            else:
                actual_args.append(arg)

            i += 1

        output = self.do_task(*actual_args)
        outputs = output if self.multiple_outputs else {'return_value': output}
        return TaskResult(self, outputs)

    def __repr__(self):
        return f'{self.name} {{ dependencies={self.dependencies}, xcom_dependencies={self.xcom_dependencies} }}'


class TaskResult:
    def __init__(self, task, results):
        self.task = task
        self.results = results

@task()
def extract():
    data_string = '{"1001": 301.27, "1002": 433.21, "1003": 502.22}'

    order_data_dict = json.loads(data_string)
    return order_data_dict

@task(multiple_outputs=True)
def transform(order_data_dict, x):
    total_order_value = 0

    for value in order_data_dict.values():
        total_order_value += value

    return {"total_order_value": total_order_value}

@task()
def load(total_order_value):
    print(f"Total order value is: {total_order_value:.2f}")

# Output:
# Total order value is: 1236.70
# Computation results: {'total_order_value': 1236.7}
# First task: extract { dependencies=[], xcom_dependencies=[] }
# Second task: transform { dependencies=['extract'], xcom_dependencies=[('extract', 'return_value')] }
# Last task: load { dependencies=['transform'], xcom_dependencies=[('transform', 'total_order_value')] }

if __name__ == '__main__':
    order_data = extract()
    order_summary = transform(order_data, 1)
    output = load(order_summary)

    print('Computation results:', order_summary.results)
    print('First task:', order_data.task)
    print('Second task:', order_summary.task)
    print('Last task:', output.task)

