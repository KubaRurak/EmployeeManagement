import React from 'react';
import { useEffect, useState } from 'react';
import { getMonthlyEarningsForLastTwoYears } from '../../../../api/StatisticsApiSerivce';
import { useTheme } from '@mui/material/styles';
import DashboardCard from '../../../components/shared/DashboardCard';
import Chart from 'react-apexcharts';


const PerformanceOverview = () => {

    const [monthlyEarnings, setMonthlyEarnings] = useState({ currentYear: [], lastYear: [] });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await getMonthlyEarningsForLastTwoYears();
                const data = response.data;


                let currentYearEarnings = Array(12).fill(0);
                let lastYearEarnings = Array(12).fill(0);

                const currentYear = new Date().getFullYear();

                data.forEach(item => {
                    if (item.year === currentYear) {
                        currentYearEarnings[item.month - 1] = item.totalMoney;
                    } else {
                        lastYearEarnings[item.month - 1] = item.totalMoney;
                    }
                });

                setMonthlyEarnings({
                    currentYear: currentYearEarnings,
                    lastYear: lastYearEarnings
                });
            } catch (error) {
                console.error('Error fetching monthly earnings:', error);
            }
        };

        fetchData();
    }, []);


    // chart color
    const theme = useTheme();
    const primary = theme.palette.primary.main;
    const secondary = theme.palette.secondary.light;

    // chart
    const optionscolumnchart = {
        chart: {
            type: 'bar',
            fontFamily: "'Plus Jakarta Sans', sans-serif;",
            foreColor: '#adb0bb',
            toolbar: {
                show: true,
            },
            height: 370,
        },
        colors: [primary, secondary],
        plotOptions: {
            bar: {
                horizontal: false,
                barHeight: '60%',
                columnWidth: '42%',
                borderRadius: [6],
                borderRadiusApplication: 'end',
                borderRadiusWhenStacked: 'all',
            },
        },

        stroke: {
            show: true,
            width: 5,
            lineCap: "butt",
            colors: ["transparent"],
          },
        dataLabels: {
            enabled: false,
        },
        legend: {
            show: false,
        },
        grid: {
            borderColor: 'rgba(0,0,0,0.1)',
            strokeDashArray: 3,
            xaxis: {
                lines: {
                    show: false,
                },
            },
        },
        yaxis: {
            tickAmount: 4,
        },
        xaxis: {
            categories: ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'],
            axisBorder: {
                show: false,
            },
        },
        tooltip: {
            theme: theme.palette.mode === 'dark' ? 'dark' : 'light',
            fillSeriesColor: false,
        },
    };
    const seriescolumnchart = [
        {
            name: `Earnings ${new Date().getFullYear()}`,
            data: monthlyEarnings.currentYear,
        },
        {
            name: `Earnings ${new Date().getFullYear() - 1}`,
            data: monthlyEarnings.lastYear,
        },
    ];

    return (

        <DashboardCard title="Earnings Overview">
            <Chart
                options={optionscolumnchart}
                series={seriescolumnchart}
                type="bar"
                height="433px"
            />
        </DashboardCard>
    );
};

export default PerformanceOverview;
