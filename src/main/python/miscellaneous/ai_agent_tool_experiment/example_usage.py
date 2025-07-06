#!/usr/bin/env python3
"""
Example usage of the Bank Statement Analyzer
"""

import subprocess
import json

def run_analyzer(filters):
    """Run the bank statement analyzer with the given filters."""
    # Prepare the input JSON
    input_json = json.dumps({"filters": filters})
    
    # Run the analyzer script with the input as a parameter
    cmd = ["python3", "bank_statement_analyzer.py", "--input", input_json]
    result = subprocess.run(cmd, capture_output=True, text=True)
    
    # Parse and return the output
    if result.returncode == 0:
        return json.loads(result.stdout)
    else:
        return {"error": result.stderr, "status": "error"}

# Example 1: Get all transactions in January 2025
print("Example 1: All transactions in January 2025")
result1 = run_analyzer([{"type": "MONTH", "query": "2025-01"}])
print(f"Found {result1['statistics']['transaction_count']} transactions")
print(f"Total amount: ${result1['statistics']['total_amount']}")
print()

# Example 2: Get all grocery store transactions
print("Example 2: All grocery store transactions")
result2 = run_analyzer([{"type": "RECIPIENT_OR_SENDER", "query": "Grocery Store"}])
print(f"Found {result2['statistics']['transaction_count']} transactions")
print(f"Total spent: ${result2['statistics']['total_amount']}")
print()

# Example 3: Get all salary deposits
print("Example 3: All salary deposits")
result3 = run_analyzer([{"type": "RECIPIENT_OR_SENDER", "query": "Salary Deposit"}])
print(f"Found {result3['statistics']['transaction_count']} transactions")
print(f"Total received: ${result3['statistics']['total_amount']}")
print()

# Example 4: Combining filters - Grocery store transactions in February
print("Example 4: Grocery store transactions in February")
result4 = run_analyzer([
    {"type": "MONTH", "query": "2025-02"},
    {"type": "RECIPIENT_OR_SENDER", "query": "Grocery Store"}
])
print(f"Found {result4['statistics']['transaction_count']} transactions")
print(f"Total spent: ${result4['statistics']['total_amount']}")
print()

# Example 5: All credits (positive amounts)
print("Example 5: All credits (positive amounts)")
result5 = run_analyzer([{"type": "CREDIT", "query": ""}])
print(f"Found {result5['statistics']['transaction_count']} credit transactions")
print(f"Total credits: ${result5['statistics']['total_amount']}")
print(f"Sources: {', '.join(result5['statistics']['unique_recipients_senders_list'])}")
print()

# Example 6: All debits (negative amounts) in March
print("Example 6: All debits in March")
result6 = run_analyzer([
    {"type": "MONTH", "query": "2025-03"},
    {"type": "DEBIT", "query": ""}
])
print(f"Found {result6['statistics']['transaction_count']} debit transactions in March")
print(f"Total debits: ${result6['statistics']['total_amount']}")
print()
