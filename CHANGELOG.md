### New features
`core-mit` module
- Added `eraseStackTrace()` method in `EncodableResult` & `WrappedException` which erases the whole stack trace in exception information
- Added `randomBase64()` method to generate random Base64 of the specified length
### Fixed Bugs
`reflection` module:
- `.encodeObject()`/`.decodeObject()` not working properly with `null`
- `.decodeObject()` not working properly with Java `Void`
- `.boxed()`/`.unboxed()` not working properly with Java `Void`