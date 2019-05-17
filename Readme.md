============================================================================

Deploy the war file to any servlet container such Tomcat Servlet Container.

For uploading file, please define post request body as below 

Key = "file", Value = file_for_upload

============================================================================


Example URL for CURD 

Create - POST /storage/documents

http://localhost:8080/fileSystemDemo/storage/documents/65C5F72C3563463B9BE3

Query - GET /storage/documents/{docId} 

http://localhost:8080/fileSystemDemo/storage/documents/65C5F72C3563463B9BE3

Update - PUT /storage/documents/{docId} 

http://localhost:8080/fileSystemDemo/storage/documents/

Delete - DELETE /storage/documents/{docId}

http://localhost:8080/fileSystemDemo/storage/documents/65C5F72C3563463B9BE3

============================================================================

