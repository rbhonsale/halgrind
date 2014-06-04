package org.valgrind.forhudson.model;

public enum ValgrindErrorKind
{
	InvalidRead,
	InvalidWrite,
	Leak_DefinitelyLost,
	Leak_PossiblyLost,
	Leak_StillReachable,
	Leak_IndirectlyLost,
	UninitCondition,
	UninitValue,
	Overlap,
	SyscallParam,
	InvalidFree,
	MismatchedFree,
}
