import { Component, computed, input, output } from '@angular/core';

@Component({
  selector: 'app-table',
  imports: [],
  templateUrl: './table.html',
  styleUrl: './table.css',
})
export class Table {
  readonly rows = input<unknown[]>([]);  

  readonly edit = output<unknown>();
  readonly remove = output<unknown>();

  readonly columns = computed(() => {
    const keys = new Set<string>();

    for (const row of this.rows()) {
      if (row && typeof row === 'object') {
        Object.keys(row).forEach((key) => keys.add(key));
      }
    }

    return Array.from(keys);
  });

  onEdit(row: unknown): void {
    this.edit.emit(row);
  }

  onDelete(row: unknown): void {
    this.remove.emit(row);
  }

  getCellValue(row: unknown, column: string): unknown {
    if (!row || typeof row !== 'object') {
      return undefined;
    }

    return (row as Record<string, unknown>)[column];
  }

  formatValue(value: unknown): string {
    if (value === null || value === undefined) {
      return '-';
    }

    if (typeof value === 'object') {
      return JSON.stringify(value);
    }

    return String(value);
  }
}
