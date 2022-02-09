// WinConsoleApp.cpp : Defines the entry point for the application.
//
#include <deque>
#include "WinConsoleApp.h"
#include "WebrtcSnippet.h"

using namespace std;

int quick_test() {

	double arrival_time_ms = 100;
	double smoothed_delay_ms = 2;
	double raw_delay_ms = 3;

	PacketTiming packet(arrival_time_ms, smoothed_delay_ms, raw_delay_ms);
	PacketTiming packet2(299, 4, 6);
	PacketTiming packet3(299, 4, 6);

	deque<PacketTiming> packets;
	packets.push_back(packet);
	packets.push_back(packet2);
	packets.push_back(packet3);

	absl::optional<double> slope = LinearFitSlope(packets);

	cout << "slope=" << slope.value() << endl;

	return 0;
}


int main()
{
	cout << "start..." << endl;
	quick_test();
	cout << "... end" << endl;
	return 0;
}
