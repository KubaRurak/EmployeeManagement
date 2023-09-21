import { IconCurrencyZloty } from '@tabler/icons-react';
import { Avatar, Card, CardContent, Stack, SvgIcon, Typography } from '@mui/material';

const TotalProfitCard = () => {

  return (
    <Card>
      <CardContent>
        <Stack
          alignItems="flex-start"
          direction="row"
          justifyContent="space-between"
          spacing={3}
        >
          <Stack spacing={1}>
            <Typography
              color="text.secondary"
              variant="overline"
            >
              TOTAL PROFIT
            </Typography>
            <Typography variant="h3">
              235k
            </Typography>
          </Stack>
          <Avatar
            sx={{
              backgroundColor: 'primary.main',
              height: 56,
              width: 56
            }}
          >
            <SvgIcon>
              <IconCurrencyZloty />
            </SvgIcon>
          </Avatar>
        </Stack>
      </CardContent>
    </Card>
  );
};

export default TotalProfitCard;


