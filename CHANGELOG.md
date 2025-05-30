### New features
`core` module
- Added `eraseStackTrace()` method in `EncodableResult` & `WrappedException` which erases the whole stack trace in exception information
### Fixed Bugs
`reflection` module:
- `.encodeObject()`/`.decodeObject()` not working properly with `null`
- `.decodeObject()` not working properly with Java `Void`
- `.boxed()`/`.unboxed()` not working properly with Java `Void`