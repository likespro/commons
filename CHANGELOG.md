### New features
`core` module:
- Add `Pagination` class to work with pagination
- Add `Repository` interface with common repository methods
- Add `Timestamp` value class for representing timestamps
`core-mit` module
- Add `DetailsConfiguration` for `WrappedException.kt` and `EncodableResult.kt` to customize exception details inclusion
- Add `randomBase64()` method to generate random Base64 of the specified length
- Add `Value` interface to represent a value in wrappers
- Add `Entity` interface to represent an entity
- Add `Validatable` interface to represent a validatable object
### Fixed Bugs
`reflection` module:
- `.encodeObject()`/`.decodeObject()` not working properly with `null`
- `.decodeObject()` not working properly with Java `Void`
- `.boxed()`/`.unboxed()` not working properly with Java `Void`