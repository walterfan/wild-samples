#ifndef __WEBRTC_SNIPPETS_H_
#define __WEBRTC_SNIPPETS_H_

#include <iostream>
#include <deque>
#include "absl/strings/match.h"
#include "absl/types/optional.h"

#define RTC_CHECK(condition)                                    \
  (condition) ? static_cast<void>(0)                            \
              : std::cout << __FILE__ << "," << __LINE__ <<":"<< #condition << std::endl

#define RTC_DCHECK(condition) RTC_CHECK(condition)

struct PacketTiming {
    PacketTiming(double arrival_time_ms,
        double smoothed_delay_ms,
        double raw_delay_ms)
        : arrival_time_ms(arrival_time_ms),
        smoothed_delay_ms(smoothed_delay_ms),
        raw_delay_ms(raw_delay_ms) {}
    double arrival_time_ms;
    double smoothed_delay_ms;
    double raw_delay_ms;
};


absl::optional<double> LinearFitSlope(
    const std::deque<PacketTiming>& packets) {
    //RTC_DCHECK(packets.size() >= 2);
    // Compute the "center of mass".
    double sum_x = 0;
    double sum_y = 0;
    for (const auto& packet : packets) {
        sum_x += packet.arrival_time_ms;
        sum_y += packet.smoothed_delay_ms;
    }
    double x_avg = sum_x / packets.size();
    double y_avg = sum_y / packets.size();
    // Compute the slope k = \sum (x_i-x_avg)(y_i-y_avg) / \sum (x_i-x_avg)^2
    double numerator = 0;
    double denominator = 0;
    for (const auto& packet : packets) {
        double x = packet.arrival_time_ms;
        double y = packet.smoothed_delay_ms;
        numerator += (x - x_avg) * (y - y_avg);
        denominator += (x - x_avg) * (x - x_avg);
    }
    if (denominator == 0)
        return absl::nullopt;
    return numerator / denominator;
}

#endif