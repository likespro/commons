### New features
`core-mit` module
- Add `DetailsConfiguration` for `WrappedException.kt` and `EncodableResult.kt` to customize exception details inclusion
- Added `randomBase64()` method to generate random Base64 of the specified length
### Fixed Bugs
`reflection` module:
- `.encodeObject()`/`.decodeObject()` not working properly with `null`
- `.decodeObject()` not working properly with Java `Void`
- `.boxed()`/`.unboxed()` not working properly with Java `Void`