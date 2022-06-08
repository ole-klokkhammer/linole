import 'dotenv/config';

export const Config = {
  ip: process.env.IP || '',
  mac: process.env.MAC || ''
};
