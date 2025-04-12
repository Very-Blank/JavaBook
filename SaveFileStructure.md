#Header
//All data is stored a little endian
//This layout will not change:
//----
FileSignature: [8]u8 = "kirjasto"
FileVersion: u8
Date: u32 // DD = u8, MM = u8, YYYY = u16,
Padding: u48 // This can be used for spesific FileVersion data, but size can't be chaged!
//----

//--- This can
// Indices to data chunks.
// Structure Of indices: {name: [4]u8, startIndex: u64}, Illegal name: {0, 0, 0, 0}
// Data will end at where the next one starts.
// If name is not recognized, ignore that data chunk.
// ---
End marker: [8]u8

#BinData
// NO OTHER DATA that is not included included in the indices can be stored HERE!

End marker: [8]u8