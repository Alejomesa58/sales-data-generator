# Sales Data Generator — First Delivery

This Eclipse (Java 11) project generates small **flat files** to be used as input by the main program of the second delivery.  
No user input is required. Running the app produces deterministic demo data under the `data/` folder.

## How to Run

1. Open the project in **Eclipse** (Execution environment: **JavaSE-11**).
2. Run `edu.project.datagen.GenerateInfoFiles` as a **Java Application**.
3. On success, the console prints: 
[OK] Basic files generated under /data
## Output Files (all paths are relative to the project root)

data/
products.csv # ProductID,ProductName,UnitPrice
salesmen_info.csv # DocType,DocNumber,FirstName,LastName
salesmen/
<DocNumber>.csv # First line: DocType,DocNumber

### Sample (first lines)

- `data/products.csv`
1001,Notebook,12.50
1002,Pen,2.00
1003,Ruler,3.25
...

- `data/salesmen_info.csv`
CC,1010,Ana,Lopez
TI,2020,Juan,Gomez
PP,3030,Luisa,Perez
...

- `data/salesmen/1010.csv`
CC,1010
1001,2
1005,1
1012,3
...

Copiar código

## Design Notes

- **Single responsibility**: only generates input files (first delivery).
- **Simple, readable dataset**: fixed lists for products and salesmen.
- **Consistent formatting**: semicolon `;` as field separator.
- **No user prompts**: complies with the assignment requirement.
- **Javadoc in English**: class and methods are documented.

## Java Version

- Project built and tested with **Java 11** (`JavaSE-11` in Eclipse).

## Next Steps (second delivery, not in this commit)

- Implement the organizer/loader program to read these files and process the data.
