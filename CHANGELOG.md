### New features
`core` module:
- Add `Pagination` class to work with pagination
- Add `Repository` interface with common repository methods
- Add `Timestamp` value class for representing timestamps
- Add `Latitude` and `Longitude` value classes for representing geographic coordinates
- Add `PlaceId` value class for representing place identifiers on Google Maps and Apple Maps
- Add `CustomGeolocation` value class for representing custom geographic locations
`core-mit` module
- Add `String.toJSONObject()` & `String.toJSONArray()` methods to parse JSON strings9
- Add `tryOrRuntimeException(block)` method. If the block (e.g., a lambda) throws an exception, it will be wrapped into `RuntimeException`
- Add `DetailsConfiguration` for `WrappedException.kt` and `EncodableResult.kt` to customize exception details inclusion
- Add `randomBase64()` method to generate random Base64 of the specified length
- Add `Value` interface to represent a value in wrappers
- Add `Entity` interface to represent an entity
- Add `Aggregate` interface to represent an aggregation
- Add `Validatable` interface to represent a validatable object
- Add `Iteration` Value class which represents an iteration number (starting from 0)
- Make `org.json:json` an `api` dependency instead of `implementation`
`reflection` module:
- `decodeObject()` now auto-validates the object if it implements `Validatable`
- Wrapped `ObjectEncoding` exceptions into `RuntimeException`
`network` module:
- Add `RESTAPIResponse` class to turn EncodableResult into REST API compatible form
- Add `EncodableResult.toResponse()` method to convert EncodableResult into REST API compatible form
### Fixed Bugs
`core-mit` module:
- Wrapping of `WrappedException` now returns the original `WrappedException`
`reflection` module:
- `.encodeObject()`/`.decodeObject()` not working properly with `null`
- `.decodeObject()` not working properly with Java `Void`
- `.boxed()`/`.unboxed()` not working properly with Java `Void`
`network` module:
- Wrapped HTTP operations exceptions into `RuntimeException`s