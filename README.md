Notes for future

26.08.2024:
1) Sequences (for IDs) are not working correctly.
2) CsvUtils::getCsvRecordsFromMultipartFile() function 
can be considered pretty hardcoded, it assumes:
   * csv delimiter is comma (','), for a better design, this should be flexible
   * the first row is header, but in another case it might not be.
3) BookServiceImpl::createBulk() function gets CsvRecords from CsvUtils::getCsvRecordsFromMultipartFile() function,
then iterate over CsvRecords and build Book object from CsvRecords using BookBuilder. For a better design, this for loop can be done in somewhere else.
4) After the for loop, BookServiceImpl::createBulk() function iterates over the book list and use
BookServiceImpl::createBook() function to create each book. However, there is a lack of design choice: what should be done if 1 Book of N Books is failed to  be created? Possible choices:
   * Ignore the Duplication Exceptions (same name&publisher) or IllegalArgumentException (negative stock) and consider only Fatal Exceptions (something went wrong unexpectedly)
     * for this use case <this.createBook(book);> line should be replaced by <bookRepository.save(book);>
   * Whenever a book candidate is failed to be created, rollback all created books previously, and tell client which book caused which problem
   * Add the complete&correct book objects to DB, and warn the client about the problematic ones and the causes
     * Might need to add some DTO for those warnings, maybe store warnings in a List<> until the end of loop. And send HttpStatus.PARTIAL if List<> is not empty.

27.08.2024:
1) Problem (26.08.2024:1) is fixed. (hibernate.id.sequence.increment_size_mismatch_strategy)
   * Solution is to add (allocationSize = 1) to all @SequenceGenerator annotations
2) Refactoring (26.08.2024:2) is done. A brand new (CsvConfig.java) file created to provide abstraction, from now on getCsvRecordsFromMultipartFile() function does not assume previously mentioned claims.
