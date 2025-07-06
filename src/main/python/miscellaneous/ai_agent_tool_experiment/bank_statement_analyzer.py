#!/usr/bin/env python3
"""
Bank Statement Analyzer

This tool analyzes a bank statement TSV file and answers questions based on filters.
Input and output are in JSON format.

Filter types:
- DATE: Filter by specific date (yyyy-MM-dd) or date range (yyyy-MM-dd:yyyy-MM-dd)
- MONTH: Filter by month (yyyy-MM) or month range (yyyy-MM:yyyy-MM)
- RECIPIENT_OR_SENDER: Filter by recipient or sender name
- DEBIT: Include only debits (negative amounts)
- CREDIT: Include only credits (positive amounts)
"""

import json
import csv
import argparse
import sys
import re
from typing import List, Dict, Any
from datetime import datetime


class BankStatementAnalyzer:
    def __init__(self, file_path: str):
        """Initialize with the path to the TSV file."""
        self.file_path = file_path
        self.transactions = self._load_transactions()
        self.filter_types_used = set()

    def _load_transactions(self) -> List[Dict[str, Any]]:
        """Load transactions from the TSV file."""
        transactions = []
        
        try:
            with open(self.file_path, 'r') as file:
                reader = csv.DictReader(file, delimiter='\t')
                for row in reader:
                    # Convert amount to float
                    amount = float(row['Amount'])
                    
                    # Create transaction object
                    transaction = {
                        'date': row['Date'],
                        'recipient_sender': row['Recipient/Sender'],
                        'amount': amount,
                        'transaction_type': 'credit' if amount >= 0 else 'debit'
                    }
                    transactions.append(transaction)
            return transactions
        except Exception as e:
            return []

    def _parse_date(self, date_str: str) -> datetime:
        """Parse a date string into a datetime object."""
        # Validate date format (yyyy-MM-dd)
        if not re.match(r'^\d{4}-\d{2}-\d{2}$', date_str):
            raise ValueError(f"Invalid date format: {date_str}. Expected format: yyyy-MM-dd")
        try:
            return datetime.strptime(date_str, '%Y-%m-%d')
        except ValueError:
            raise ValueError(f"Invalid date: {date_str}. Please use a valid date in yyyy-MM-dd format.")
    
    def _parse_month(self, month_str: str) -> datetime:
        """Parse a month string into a datetime object."""
        # Validate month format (yyyy-MM)
        if not re.match(r'^\d{4}-\d{2}$', month_str):
            raise ValueError(f"Invalid month format: {month_str}. Expected format: yyyy-MM")
        try:
            return datetime.strptime(month_str, '%Y-%m')
        except ValueError:
            raise ValueError(f"Invalid month: {month_str}. Please use a valid month in yyyy-MM format.")

    def apply_filters(self, filters: List[Dict[str, str]]) -> List[Dict[str, Any]]:
        """Apply the specified filters to the transactions."""
        if not filters:
            return self.transactions
        
        filtered_transactions = self.transactions.copy()
        self.filter_types_used = set()
        
        for filter_item in filters:
            filter_type = filter_item.get('type', '').upper()
            query = filter_item.get('query', '')
            
            # Check for duplicate filter types
            if filter_type in self.filter_types_used:
                raise ValueError(f"Multiple filters of the same type '{filter_type}' are not supported. Use range syntax instead.")
            
            self.filter_types_used.add(filter_type)
            
            if filter_type == 'DATE':
                if ':' in query:  # Date range
                    start_date_str, end_date_str = query.split(':')
                    try:
                        start_date = self._parse_date(start_date_str)
                        end_date = self._parse_date(end_date_str)
                        
                        # Validate that end date is not before start date
                        if end_date < start_date:
                            raise ValueError(f"End date ({end_date_str}) must be greater than or equal to start date ({start_date_str})")
                            
                        filtered_transactions = [
                            t for t in filtered_transactions 
                            if start_date <= self._parse_date(t['date']) <= end_date
                        ]
                    except ValueError as e:
                        raise ValueError(f"Invalid date range: {e}")
                else:  # Single date
                    try:
                        parsed_date = self._parse_date(query)
                        filtered_transactions = [t for t in filtered_transactions if t['date'] == query]
                    except ValueError as e:
                        raise ValueError(f"Invalid date: {e}")
            
            elif filter_type == 'MONTH':
                if ':' in query:  # Month range
                    start_month_str, end_month_str = query.split(':')
                    try:
                        start_month = self._parse_month(start_month_str)
                        end_month = self._parse_month(end_month_str)
                        
                        # Validate that end month is not before start month
                        if end_month < start_month:
                            raise ValueError(f"End month ({end_month_str}) must be greater than or equal to start month ({start_month_str})")
                        
                        filtered_transactions = [
                            t for t in filtered_transactions 
                            if start_month <= self._parse_month(t['date'][:7]) <= end_month
                        ]
                    except ValueError as e:
                        raise ValueError(f"Invalid month range: {e}")
                else:  # Single month
                    try:
                        parsed_month = self._parse_month(query)
                        filtered_transactions = [t for t in filtered_transactions if t['date'].startswith(query)]
                    except ValueError as e:
                        raise ValueError(f"Invalid month: {e}")
            
            elif filter_type == 'RECIPIENT_OR_SENDER':
                filtered_transactions = [t for t in filtered_transactions if query.lower() in t['recipient_sender'].lower()]
            
            elif filter_type == 'DEBIT':
                filtered_transactions = [t for t in filtered_transactions if t['amount'] < 0]
            
            elif filter_type == 'CREDIT':
                filtered_transactions = [t for t in filtered_transactions if t['amount'] > 0]
            
            else:
                raise ValueError(f"Unsupported filter type: {filter_type}")
        
        return filtered_transactions

    def analyze(self, filters: List[Dict[str, str]]) -> Dict[str, Any]:
        """Analyze transactions based on filters and return statistics."""
        filtered_transactions = self.apply_filters(filters)
        
        # Calculate statistics
        total_amount = sum(t['amount'] for t in filtered_transactions)
        total_credits = sum(t['amount'] for t in filtered_transactions if t['amount'] > 0)
        total_debits = sum(t['amount'] for t in filtered_transactions if t['amount'] < 0)
        
        # Count transactions by type
        credit_count = sum(1 for t in filtered_transactions if t['amount'] > 0)
        debit_count = sum(1 for t in filtered_transactions if t['amount'] < 0)
        
        # Get unique recipients/senders
        unique_parties = set(t['recipient_sender'] for t in filtered_transactions)
        
        return {
            'transactions': filtered_transactions,
            'statistics': {
                'transaction_count': len(filtered_transactions),
                'total_amount': round(total_amount, 2),
                'total_credits': round(total_credits, 2),
                'total_debits': round(total_debits, 2),
                'credit_count': credit_count,
                'debit_count': debit_count,
                'unique_recipients_senders': len(unique_parties),
                'unique_recipients_senders_list': list(unique_parties)
            }
        }


def print_detailed_help():
    """Print detailed help information in an LLM-friendly format."""
    help_text = """
Bank Statement Analyzer - Comprehensive Usage Guide
==================================================

OVERVIEW:
---------
This tool analyzes bank statement data from a TSV file and provides insights based on specified filters.
It processes transaction data and returns statistics in JSON format, making it suitable for both human
and LLM consumption.

COMMAND USAGE:
-------------
python bank_statement_analyzer.py --input <json_input>

PARAMETERS:
----------
--input    Required. JSON string containing filter specifications
-h, --help Show this detailed help message

JSON INPUT STRUCTURE:
-------------------
{
  "filters": [
    {"type": "FILTER_TYPE", "query": "FILTER_VALUE"},
    ...
  ]
}

SUPPORTED FILTER TYPES:
---------------------
1. DATE
   - Filters transactions by exact date or date range
   - Format must be yyyy-MM-dd (e.g., 2023-05-15)
   - Single date example: {"type": "DATE", "query": "2023-05-15"}
   - Date range example: {"type": "DATE", "query": "2023-05-01:2023-05-31"}

2. MONTH
   - Filters transactions by month or month range
   - Format must be yyyy-MM (e.g., 2023-05)
   - Single month example: {"type": "MONTH", "query": "2023-05"}
   - Month range example: {"type": "MONTH", "query": "2023-01:2023-06"}

3. RECIPIENT_OR_SENDER
   - Filters transactions by recipient or sender name (case-insensitive substring match)
   - Example: {"type": "RECIPIENT_OR_SENDER", "query": "Amazon"}

4. DEBIT
   - Includes only debit transactions (negative amounts)
   - Example: {"type": "DEBIT"}

5. CREDIT
   - Includes only credit transactions (positive amounts)
   - Example: {"type": "CREDIT"}

FILTER COMBINATIONS:
------------------
Multiple filters of DIFFERENT types can be combined to narrow down results. Filters are applied sequentially.
Multiple filters of the SAME type are NOT supported - use range syntax instead.

LIMITATIONS:
----------
1. Cannot use multiple filters of the same type (e.g., two DATE filters)
   - Use date/month range syntax instead (e.g., "2023-01-01:2023-12-31")
2. Cannot perform complex logical operations (OR, NOT) between filters
3. Cannot sort results (results are returned in the original order from the file)
4. Cannot perform aggregations by custom time periods or categories
5. Cannot export results to other formats (only JSON output is supported)
6. Date format must be yyyy-MM-dd (e.g., 2023-05-15)
7. Month format must be yyyy-MM (e.g., 2023-05)
8. In date/month ranges, the end date/month must be greater than or equal to the start date/month

OUTPUT FORMAT:
------------
The tool returns a JSON object with:
1. transactions: List of filtered transactions
2. statistics:
   - transaction_count: Total number of transactions
   - total_amount: Sum of all transaction amounts
   - total_credits: Sum of all credit amounts
   - total_debits: Sum of all debit amounts
   - credit_count: Number of credit transactions
   - debit_count: Number of debit transactions
   - unique_recipients_senders: Count of unique parties
   - unique_recipients_senders_list: List of unique parties

EXAMPLE USAGE:
------------
1. Filter by month and transaction type:
   python3 bank_statement_analyzer.py --input '{"filters":[{"type":"MONTH","query":"2023-05"},{"type":"DEBIT"}]}'

2. Filter by date range:
   python3 bank_statement_analyzer.py --input '{"filters":[{"type":"DATE","query":"2023-05-01:2023-05-31"}]}'

3. Filter by month range and recipient:
   python3 bank_statement_analyzer.py --input '{"filters":[{"type":"MONTH","query":"2023-01:2023-06"},{"type":"RECIPIENT_OR_SENDER","query":"Amazon"}]}'

ERROR HANDLING:
-------------
The tool returns JSON-formatted error messages for:
- Invalid JSON input
- Multiple filters of the same type
- Unsupported filter types
- Invalid date or month formats
- File access issues
- Other exceptions during processing
"""
    print(help_text)

def main():
    """Main function to process input and generate output."""
    # Check for -h flag before argparse to provide custom help
    if '-h' in sys.argv or '--help' in sys.argv:
        print_detailed_help()
        sys.exit(0)
        
    parser = argparse.ArgumentParser(description='Analyze bank statement data with filters')
    parser.add_argument('--input', type=str, required=True, help='JSON input string with filters')
    args = parser.parse_args()
    
    try:
        # Parse input JSON from command line argument
        input_data = json.loads(args.input)
        
        # Extract filters from input
        filters = input_data.get('filters', [])
        
        # Create analyzer and process
        analyzer = BankStatementAnalyzer('mock-statement.tsv')
        result = analyzer.analyze(filters)
        
        # Output JSON result
        print(json.dumps(result, indent=2))
        
    except json.JSONDecodeError:
        error = {
            'error': 'Invalid JSON input',
            'status': 'error'
        }
        print(json.dumps(error, indent=2))
    except ValueError as e:
        error = {
            'error': str(e),
            'status': 'error'
        }
        print(json.dumps(error, indent=2))
    except Exception as e:
        error = {
            'error': str(e),
            'status': 'error'
        }
        print(json.dumps(error, indent=2))


if __name__ == "__main__":
    main()
