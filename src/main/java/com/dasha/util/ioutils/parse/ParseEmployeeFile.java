package com.dasha.util.ioutils.parse;

import java.io.File;
import java.util.List;

public interface ParseEmployeeFile {

    List<EmployeeParsed> read(File file);
}
